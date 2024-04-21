import boto3
from flask_cors import CORS

from config import AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_REGION, BUCKET_NAME
import mimetypes
from flask import Flask, request, jsonify, Response
import service.skills_service as service
from service import udemy_service, skills_service, s3_service

app = Flask(__name__)
CORS(app)

s3_client = boto3.client(
    's3',
    region_name=AWS_REGION,
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY
)


@app.route('/analyze', methods=[ 'POST' ])
def process_and_analyze():
    print("Received a request to analyze.")

    # Check if the file part is present in the request
    if 'file' not in request.files:
        print("Error: No file part in request.")
        return jsonify(success=False, message="No file part"), 400

    file = request.files[ 'file' ]

    # Check if a filename is not empty (file is selected)
    if file.filename == '':
        print("Error: No file selected.")
        return jsonify(success=False, message="No selected file"), 400

    # Check if the job description is provided
    if 'job_description' not in request.form:
        print("Error: No job description provided.")
        return jsonify(success=False, message="No job description provided"), 400

    job_description = request.form[ 'job_description' ]
    print(f"Job Description: {job_description}")

    # Process the file if it's allowed
    if file and allowed_file(file.filename):
        success, message, filename = s3_service.upload_file(file)
        print(f"File upload status: {success}, Message: {message}, Filename: {filename}")

        if not success:
            return jsonify(success=success, message=message), 500

        # Classify the resume to get missing skills
        result = skills_service.classify_resume(filename, job_description)
        print(f"Classified Results: {result}")

        # Fetch course details for missing hard skills
        res = udemy_service.get_course_details(result[ 'missing_hard_skills' ])
        print(f"Udemy Courses Response: {res}")

        merged_result = {
            'udemy_courses': res,
            'missing_hard_skills': result[ 'missing_hard_skills' ],
            'missing_soft_skills': result[ 'missing_soft_skills' ]
        }

        print("Sending merged result.")
        return jsonify(merged_result)
    else:
        print("File not allowed.")
        return jsonify(message="File not allowed"), 400


def allowed_file(filename):
    # Checks if the file has an extension and if it ends with '.pdf'
    return '.' in filename and filename.rsplit('.', 1)[1].lower() == 'pdf'

if __name__ == '__main__':
    app.run(debug=True)
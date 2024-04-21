import time
import os

import boto3

from config import AWS_REGION, AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, BUCKET_NAME

s3_client = boto3.client(
    's3',
    region_name=AWS_REGION,
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY
)
def upload_file(file):
    try:
        timestamp = int(round(time.time() * 1000))

        original_filename = file.filename
        new_filename = f"{timestamp}_{original_filename}"

        s3_client.put_object(Bucket=BUCKET_NAME, Key=new_filename, Body=file)
        return True, "File uploaded successfully.", new_filename
    except Exception as e:
        return False, str(e), None


def get_file(file_name):
    try:
        response = s3_client.get_object(Bucket=BUCKET_NAME, Key=file_name)
        return response['Body'].read(), response['ContentType']
    except Exception as e:
        return None, str(e)

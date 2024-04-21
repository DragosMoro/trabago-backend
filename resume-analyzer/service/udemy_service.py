import json
import random
import requests
from config import API_URL, AUTHORIZATION

def fetch_course_details(skill):
    url = f"{API_URL}?search={skill}"
    headers = {
        "Authorization": AUTHORIZATION,
        "Content-Type": "application/json"
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        data = response.json()
        if data["results"]:
            course = data["results"][0]
            instructor = course.get("visible_instructors")[0] if course.get("visible_instructors") else {"title": "Unknown"}
            return {
                "title": course.get("title"),
                "imageUrl": course.get("image_480x270"),
                "courseUrl": "https://www.udemy.com" + course.get("url"),
                "instructorName": instructor.get("title")
            }
    else:
        return {"error": "Failed to fetch course details", "statusCode": response.status_code}

def get_course_details(missing_skills):
    prepared_skills = prepare_skills_list(missing_skills)
    courses_list = [fetch_course_details(skill) for skill in prepared_skills]
    # Filter out any error messages to ensure we only return course details
    courses_list = [course for course in courses_list if not course.get("error")]
    return  courses_list

def prepare_skills_list(skills):
    skills = skills[:]  # Create a copy to prevent modifying the original list
    if len(skills) > 4:
        return random.sample(skills, 4)
    while len(skills) < 4:
        skills.extend(skills[:4 - len(skills)])
    return skills

# Example usage
missing_hard_skills = ["Python", "Data Science", "Machine Learning"]  # Replace with actual skills
print(get_course_details(missing_hard_skills))

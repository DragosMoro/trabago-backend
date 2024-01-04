import PyPDF2
import re
import nltk
from sklearn.feature_extraction.text import TfidfVectorizer
import pickle
import sys
from nltk.corpus import stopwords


def cleanResume(resumeText):
    resumeText = re.sub('<.*?>', ' ', resumeText)  # remove HTML tags
    resumeText = re.sub('http\S+\s*', ' ', resumeText)  # remove URLs
    resumeText = re.sub('RT|cc', ' ', resumeText)  # remove RT and cc
    resumeText = re.sub('#\S+', '', resumeText)  # remove hashtags
    resumeText = re.sub('@\S+', '  ', resumeText)  # remove mentions
    resumeText = re.sub('[%s]' % re.escape("""!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~"""), ' ',
                        resumeText)  # remove punctuations
    resumeText = re.sub(r'[^\x00-\x7f]', r' ', resumeText)  # remove non-ASCII chars
    resumeText = re.sub('\s+', ' ', resumeText)  # remove extra whitespace
    return resumeText.strip()  # remove spaces at the beginning and at the end of the string


def classify_resume(file_path):
    with open(file_path, 'rb') as pdf_file:
        pdf_reader = PyPDF2.PdfReader(pdf_file)
        resume_text = ''
        for page_num in range(len(pdf_reader.pages)):
            page = pdf_reader.pages[ page_num ]
            resume_text += page.extract_text()

    cleaned_resume = cleanResume(resume_text)

    # Load the trained model, vectorizer, and label encoder
    with open("E:\\TrabaGo\\trabago-backend\\resume-analyzer\\model_logistic_regression.pkl", 'rb') as model_file, open("E:\\TrabaGo\\trabago-backend\\resume-analyzer\\vectorizer_logistic_regression.pkl",
                                                                         'rb') as vectorizer_file, open(
        "E:\\TrabaGo\\trabago-backend\\resume-analyzer\\label_encoder.pkl", 'rb') as le_file:
        clf = pickle.load(model_file)
        word_vectorizer = pickle.load(vectorizer_file)
        le = pickle.load(le_file)

    # Preprocess the text
    cleaned_resume = cleanResume(resume_text)
    text_for_classification = [ cleaned_resume ]

    # Transform the text using the pre-trained TfidfVectorizer
    text_features = word_vectorizer.transform(text_for_classification)

    # Make predictions with probabilities
    probabilities = clf.predict_proba(text_features)
    top_categories_indices = (-probabilities[ 0 ]).argsort()[ :3 ]  # Indices of top 3 categories
    top_categories = le.inverse_transform(top_categories_indices)  # Convert indices back to category labels
    top_probabilities = probabilities[ 0, top_categories_indices ]  # Probabilities of top 3 categories

    for category, probability in zip(top_categories, top_probabilities):
        print(f'{category}:{100 * probability:.4f}')



if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python script.py <file_path>")
        sys.exit(1)

    file_path = sys.argv[1]
    classify_resume(file_path)
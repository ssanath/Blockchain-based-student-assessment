import React, { useState } from 'react';
import axios from 'axios';
import '../SubmissionForm.css';
const SubmissionForm = ({ submissionCount, updateSubmissionCount }) => {
    const [content, setContent] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();
        const submission = { 
            id: Date.now().toString(), 
            content: content
        };
        try {
            await axios.post('http://localhost:8081/api/submissions/submit', submission);
            alert("Submission successful");
            updateSubmissionCount(submissionCount + 1);
        } catch (error) {
            console.error("There was an error!", error);
        }
    };

    return (
        <div>
            <p>Number of submissions: {submissionCount}</p>
            <form className="form-container" onSubmit={handleSubmit}>
                <textarea 
                    value={content} 
                    onChange={(e) => setContent(e.target.value)} 
                    placeholder="Enter your submission">
                </textarea><br/>
                <button className='submit-button' type="submit">Submit</button>
            </form>
        </div>
    );
};

export default SubmissionForm;
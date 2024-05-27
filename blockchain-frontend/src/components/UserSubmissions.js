import React, { useEffect, useState } from 'react';
import axios from 'axios';

const UserSubmissions = () => {
    const [submissions, setSubmissions] = useState([]);

    useEffect(() => {
        const fetchSubmissions = async () => {
            try {
                const response = await axios.get('http://localhost:8081/api/submissions/user');
                setSubmissions(response.data);
            } catch (error) {
                console.error('Error fetching submissions:', error);
            }
        };
        fetchSubmissions();
    }, []);

    return (
        <div>
            <h2>Your Submissions</h2>
            {submissions.map((submission, index) => (
                <div key={index}>
                    <p>ID: {submission.id}</p>
                    <p>Content: {submission.content}</p>
                    <p>Status: {submission.successful && submission.certificate ? 'Successful' : 'Unsuccessful'}</p>
                    <p>Feedback: {submission.feedback}</p>
                    {submission.successful && submission.certificate ? (
                        <p>Awarded: {submission.certificate}</p>
                    ) : (
                        <p>No certificate has been awarded</p>
                    )}
                    <hr />
                </div>
            ))}
        </div>
    );
};

export default UserSubmissions;

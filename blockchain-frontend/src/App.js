import React, { useState, useEffect } from 'react';
import SubmissionForm from './components/SubmissionForm';
import Blockchain from './components/Blockchain';
import UserSubmissions from './components/UserSubmissions';
import Navbar from './components/Navbar';
import './App.css';
const App = () => {
    const [submissionCount, setSubmissionCount] = useState(0);

    useEffect(() => {
        // Reset submission count to zero on component mount
        setSubmissionCount(0);
    }, []);
    return (
        <div>
            <Navbar />
            <center><SubmissionForm 
                submissionCount={submissionCount} 
                updateSubmissionCount={setSubmissionCount} 
            /></center>
            <Blockchain />
            <UserSubmissions />
        </div>
    );
};

export default App;

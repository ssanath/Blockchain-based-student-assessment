// src/components/Navbar.js
import React from 'react';
import '../Navbar.css';
import logo from '../logo.png';

const Navbar = () => {
    return (
        <nav className="navbar">
            <h1>Blockchain Submission System</h1>
            <img src={logo} alt="Logo" className="navbar-logo" />
        </nav>
    );
};

export default Navbar;

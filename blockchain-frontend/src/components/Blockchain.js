// src/components/Blockchain.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Blockchain = () => {
    const [chain, setChain] = useState([]);

    useEffect(() => {
        const fetchChain = async () => {
            const response = await axios.get('http://localhost:8081/api/submissions/chain');
            setChain(response.data);
        };
        fetchChain();
    }, []);

    return (
        <div>
            <h2>Blockchain</h2>
            {chain.map((block, index) => (
                <div key={index}>
                    <p>Hash: {block.hash}</p>
                    <p>Previous Hash: {block.previousHash}</p>
                </div>
            ))}
        </div>
    );
};

export default Blockchain;

const express = require('express');
const app = express();
const port = 8081;

app.use(express.json());

let blockchain = [];

app.post('/api/submissions/submit', (req, res) => {
    const { id, content, timestamp } = req.body;
    if (!id || !content || !timestamp) {
        return res.status(400).json({ error: 'Missing id, content, or timestamp' });
    }
    const newBlock = {
        hash: generateHash(),  // Assuming generateHash() is a function to create a hash
        previousHash: blockchain.length > 0 ? blockchain[blockchain.length - 1].hash : '0',
        data: { id, content, timestamp },
        timestamp: Date.now().toString()
    };
    blockchain.push(newBlock);
    res.status(201).json(newBlock);
});

app.get('/api/submissions/chain', (req, res) => {
    res.json(blockchain);
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});

function generateHash() {
    // A simple hash generation logic for illustration
    return (Math.random().toString(36).substr(2, 9) + Date.now()).toString();
}

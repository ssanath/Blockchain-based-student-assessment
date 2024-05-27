package com.sanath.blockchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Utility class for generating SHA-256 hash
class StringUtil {
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

// Class representing a block in the blockchain
class Block {
    private String hash;
    private String previousHash;
    private String data; // Can be any data, for now it's a string
    private long timestamp;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(previousHash + Long.toString(timestamp) + data);
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

// Class representing a submission
class Submission {
    private String id;
    public String content;
    private boolean revised;
    private boolean evaluated;
    private boolean successful;
    private String certificate;
    private String feedback;

    public Submission(String id, String content) {
        this.id = id;
        this.content = content;
        this.revised = false;
        this.evaluated = false;
        this.successful = false;
        this.certificate = "";
        this.feedback = "";
    }

    // Getter and setter methods
    public String getId() {
        return id;
    }

    public boolean isRevised() {
        return revised;
    }

    public void setRevised(boolean revised) {
        this.revised = revised;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", revised=" + revised +
                ", evaluated=" + evaluated +
                ", successful=" + successful +
                ", certificate='" + certificate + '\'' +
                '}';
    }

}

class submissionChain {
    private List<Submission> submissions;

    public submissionChain() {
        submissions = new ArrayList<>();
    }

    public void addSubmission(Submission submission)
    {
        submissions.add(submission);
    }

    public List<Submission> getChain() {
        return submissions;
    }

}
// Class representing a marking service
class MarkingService {
    // Method to grade submission
    public double gradeSubmission(Submission submission) {
        String content = submission.content;
        int wordCount = content.split("\\s+").length; // Count the number of words

        // Define some keywords to check in the submission content
        String[] keywords = {"blockchain", "security", "distributed", "ledger"};
        int keywordCount = 0;
        for (String keyword : keywords) {
            if (content.toLowerCase().contains(keyword.toLowerCase())) {
                keywordCount++;
            }
        }

        // Calculate grade based on word count and keyword presence
        double grade = (wordCount * 0.5) + (keywordCount * 10);
        grade = Math.min(grade, 100); // Cap the grade at 100
        submission.setFeedback("Your submission received a grade of " + grade);
        submission.setSuccessful(true);
        return grade;
    }
}

// Class representing a blockchain
class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();
    }
	
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock = new Block(newBlock.toString(), getLatestBlock().getHash());
        chain.add(newBlock);
    }

    public void addSubmission(Submission submission) {
        if(chain.size() == 0) {Block newBlock = new Block(submission.toString(), "0"); chain.add(newBlock);}
        else {Block newBlock = new Block(submission.toString(), getLatestBlock().getHash()); chain.add(newBlock);}
        System.out.println("Submission added to the blockchain.");
    }

    public void assessSubmission(Submission submission) {
        // Retrieve test files from the blockchain and send for grading
        MarkingService markingService = new MarkingService();
        double grade = markingService.gradeSubmission(submission);

        // Update submission based on grading results
        if (grade >= 30) {
            submission.setRevised(true);
            submission.setEvaluated(true);
            submission.setSuccessful(true);
            System.out.println("Submission successfully evaluated. Grade: " + grade);

            // Update certificate if submission is successful
            updateCertificate(submission);

            // Add the revised submission to the blockchain
            addSubmission(submission);
        } else {
            System.out.println("Submission evaluation failed. Grade: " + grade);
        }
    }

    // Method to update certificate
    private void updateCertificate(Submission submission) {
        // Generate certificate based on submission details
        String certificate = "Certificate of Completion for submission ID: " + submission.getId();

        // Set the generated certificate to the submission
        submission.setCertificate(certificate);
        System.out.println("Certificate updated for submission ID: " + submission.getId());
    }

    // Method to print the blockchain
    public void printBlockchain() {
        System.out.println("Blockchain contents:");
        for (Block block : chain) {
            System.out.println(block);
        }
    }
    public List<Block> getChain() {
        return chain;
    }
}

public class BlockchainDemo {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        // Create a new submission
        Submission submission = new Submission("1", "Sample submission content");

        // Assess the submission
        blockchain.assessSubmission(submission);

        // Print the blockchain
        blockchain.printBlockchain();
    }
}


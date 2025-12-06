import React, { useState } from "react";
import axios from "axios";
import { TextField, Button, Typography, Box } from "@mui/material";

export default function ProposalPage() {
  const [vendorId, setVendorId] = useState("");
  const [rfpId, setRfpId] = useState("");
  const [text, setText] = useState("");

  const submitProposal = async () => {
    if (!vendorId || !rfpId || !text.trim()) {
      alert("Please fill all fields");
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/proposal/submit", {
        vendorId,
        rfpId,
        proposalText: text,
      });

      alert("Proposal submitted!");
      setText("");
    } catch (err) {
      alert("Error submitting proposal");
    }
  };

  return (
    <Box sx={{ mt: 4 }}>
      <Typography variant="h5" sx={{ fontWeight: "bold", mb: 2 }}>
        Submit Proposal
      </Typography>

      <Box sx={{ display: "flex", gap: 2, mb: 2 }}>
        <TextField
          label="Vendor ID"
          value={vendorId}
          onChange={(e) => setVendorId(e.target.value)}
          sx={{ width: 120 }}
        />

        <TextField
          label="RFP ID"
          value={rfpId}
          onChange={(e) => setRfpId(e.target.value)}
          sx={{ width: 120 }}
        />
      </Box>

      <TextField
        label="Proposal Text"
        multiline
        rows={3}
        fullWidth
        value={text}
        onChange={(e) => setText(e.target.value)}
      />

      <Button
        variant="contained"
        sx={{ mt: 2, backgroundColor: "#1A73E8" }}
        onClick={submitProposal}
      >
        Submit Proposal
      </Button>
    </Box>
  );
}

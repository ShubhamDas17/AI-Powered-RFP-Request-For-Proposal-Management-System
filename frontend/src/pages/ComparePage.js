import React, { useState } from "react";
import axios from "axios";
import {
  TextField,
  Button,
  Typography,
  Box,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
} from "@mui/material";

export default function ComparePage() {
  const [rfpId, setRfpId] = useState("");
  const [result, setResult] = useState([]);

  const compare = async () => {
    if (!rfpId.trim()) {
      alert("Please enter RFP ID");
      return;
    }

    try {
      const res = await axios.get(
        `http://localhost:8080/api/proposal/rfp/${rfpId}/compare`
      );
      setResult(res.data);
    } catch (err) {
      alert("Error fetching comparison data");
      console.error(err);
    }
  };

  return (
    <Box sx={{ mt: 4 }}>
      <Typography variant="h5" sx={{ fontWeight: "bold", mb: 2 }}>
        Compare Proposals
      </Typography>

      <Box sx={{ display: "flex", gap: 2, mb: 3 }}>
        <TextField
          label="RFP ID"
          value={rfpId}
          onChange={(e) => setRfpId(e.target.value)}
          sx={{ width: 120 }}
        />

        <Button
          variant="contained"
          sx={{ backgroundColor: "#1A73E8" }}
          onClick={compare}
        >
          Compare
        </Button>
      </Box>

      {result.length === 0 ? (
        <Typography>No proposals found.</Typography>
      ) : (
        <Paper elevation={2}>
          <Table>
            <TableHead sx={{ backgroundColor: "#f1f5fa" }}>
              <TableRow>
                <TableCell><b>Vendor</b></TableCell>
                <TableCell><b>Score</b></TableCell>
                <TableCell><b>Analysis</b></TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {result.map((r, i) => (
                <TableRow key={i} hover>
                  <TableCell>{r.vendor}</TableCell>
                  <TableCell>{r.score}</TableCell>
                  <TableCell>
                    {r.analysis || r.reason || "No analysis"}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>
      )}
    </Box>
  );
}

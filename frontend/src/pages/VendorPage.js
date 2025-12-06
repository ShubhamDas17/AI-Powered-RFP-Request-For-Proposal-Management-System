import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  TextField,
  Button,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
  Typography,
  Box,
} from "@mui/material";

export default function VendorPage() {
  const [vendors, setVendors] = useState([]);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const loadVendors = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/vendor/list");

      const clean = res.data.filter(
        (v) => v && v.name && v.name.trim() !== ""
      );

      setVendors(clean);
    } catch (error) {
      console.error("Error loading vendors:", error);
    }
  };

  const addVendor = async () => {
    if (!name.trim() || !email.trim()) {
      alert("Vendor name and email cannot be empty");
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/vendor/add", { name, email });
      setName("");
      setEmail("");
      loadVendors();
    } catch (error) {
      alert("Error adding vendor");
      console.error(error);
    }
  };

  const deleteVendor = async (id) => {
    if (!window.confirm("Are you sure you want to delete this vendor?")) return;

    try {
      await axios.delete(`http://localhost:8080/api/vendor/delete/${id}`);
      loadVendors();
    } catch (error) {
      alert("Error deleting vendor");
      console.error(error);
    }
  };

  useEffect(() => {
    loadVendors();
  }, []);

  return (
    <Box sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h5" sx={{ mb: 2, fontWeight: "bold" }}>
        Vendor Management
      </Typography>

      <Box sx={{ display: "flex", gap: 2, mb: 3 }}>
        <TextField
          label="Vendor Name"
          size="small"
          sx={{ width: 200 }}
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <TextField
          label="Email"
          size="small"
          sx={{ width: 230 }}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <Button variant="contained" onClick={addVendor}>
          Add Vendor
        </Button>
      </Box>

      <Paper elevation={2}>
        <Table>
          <TableHead sx={{ background: "#f3f6fb" }}>
            <TableRow>
              <TableCell><b>ID</b></TableCell>
              <TableCell><b>Vendor Name</b></TableCell>
              <TableCell><b>Email</b></TableCell>
              <TableCell><b>Action</b></TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {vendors.map((v) => (
              <TableRow key={v.id} hover>
                <TableCell>{v.id}</TableCell>
                <TableCell>{v.name}</TableCell>
                <TableCell>{v.email}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => deleteVendor(v.id)}
                  >
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </Paper>
    </Box>
  );
}

import React, { useState } from "react";
import axios from "axios";

export default function RFPPage() {
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const createRFP = async () => {
    if (!description.trim()) {
      alert("Please enter description");
      return;
    }

    try {
      setLoading(true);
      setError("");
      setResult(null);

      const res = await axios.post("http://localhost:8080/api/rfp/create", {
        description,
      });

      setResult(res.data);
    } catch (err) {
      console.error(err);
      setError("Error creating RFP. Check backend logs.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "0 auto" }}>
      <h2>Create RFP</h2>

      <textarea
        rows="5"
        style={{
          width: "100%",
          padding: "10px",
          border: "1px solid #bbb",
          borderRadius: "6px",
        }}
        placeholder="Enter RFP description..."
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <br />

      <button
        onClick={createRFP}
        disabled={loading}
        style={{
          padding: "10px 16px",
          marginTop: "10px",
          background: loading ? "#777" : "black",
          color: "white",
          fontWeight: "bold",
          borderRadius: "6px",
          cursor: loading ? "not-allowed" : "pointer",
          border: "none",
        }}
      >
        {loading ? "Processing..." : "Create RFP"}
      </button>

      {error && (
        <p style={{ color: "red", marginTop: "10px" }}>
          {error}
        </p>
      )}

      {result && (
        <pre
          style={{
            background: "#f5f5f5",
            padding: "12px",
            marginTop: "20px",
            borderRadius: "8px",
            whiteSpace: "pre-wrap",
            wordBreak: "break-word",
          }}
        >
          {JSON.stringify(result, null, 2)}
        </pre>
      )}
    </div>
  );
}

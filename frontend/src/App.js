import React from "react";
import RFPPage from "./pages/RFPPage";
import VendorPage from "./pages/VendorPage";
import ProposalPage from "./pages/ProposalPage";
import ComparePage from "./pages/ComparePage";
import { Box, Typography } from "@mui/material";

export default function App() {
  return (
    <Box sx={{ p: 4 }}>
      <Typography variant="h4" sx={{ fontWeight: "bold", mb: 4 }}>
        AI Powered RFP System
      </Typography>

      <RFPPage />
      <hr />

      <VendorPage />
      <hr />

      <ProposalPage />
      <hr />

      <ComparePage />
    </Box>
  );
}

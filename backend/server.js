const express = require("express");
const cors  = require("cors");
const mongoose = require("mongoose");
const UserRoutes = require("./routes/UserRoutes");
const ProjectRoutes = require("./routes/ProjectRoutes");
// const AuthRoutes = require("./routes/AuthRoutes");
const dbConfig = require("./config/dbConfig");


const app = express();
app.use(express.json());
app.use(cors());

const PORT = process.env.PORT || 3000;

app.use("/user", UserRoutes);
app.use("/project", ProjectRoutes);
// app.use("/auth", AuthRoutes);

// mongoose.connect(dbConfig.mongoURI, {useNewURLParser: true, useUnifiedTopoology: true})
// .then(() => {console.log("Database Connected")})
// .catch(err => {console.error("Database Connection Error: ", err)});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
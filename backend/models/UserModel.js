const mongoose = require("mongoose");
const Project = require("./ProjectModel");

const Schema = mongoose.Schema;

const UserSchema = new Schema({
    email: String,
    password: String,
    firstName: String, 
    lastname: String,
    role: String,
    project: {type: Schema.Types.ObjectId, ref: "Project"}
});

const User = mongoose.model("User", UserSchema);

module.exports = User;
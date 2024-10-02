const mongoose = require("mongoose");
const {isEmail} = require("validator");
const Project = require("./ProjectModel");

const Schema = mongoose.Schema;

const UserSchema = new Schema({
    email: {type: String, required: true, unique: true, lowercase: true, index: true, validate: isEmail},
    password: {type: String, required: true},
    firstName: {type: String, required: true}, 
    lastname: {type: String, required: true},
    role: {type: String, required: true},
    project: {type: Schema.Types.ObjectId, ref: "Project"}
});

const User = mongoose.model("User", UserSchema);

module.exports = User;
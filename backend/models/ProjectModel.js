const mongoose = require("mongoose");
const User = require("./UserModel");

const Schema = mongoose.Schema;

const ProjectSchema = new Schema({
    name: String,
    description: String,
    admin: {type: Schema.Types.ObjectId, ref: "User"}
});

const Project = mongoose.model("Project", ProjectSchema);

module.exports = Project;
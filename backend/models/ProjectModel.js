const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const ProjectSchema = new Schema({
    name: String,
    tasks: [{type: Schema.Types.ObjectId, ref: "Task"}],
    contributors: [{type: Schema.Types.ObjectId, ref: "User"}],
    admin: {type: Schema.Types.ObjectId, ref: "User"}
});

const ProjectModel = mongoose.model("Project", ProjectSchema);

module.exports = ProjectModel;
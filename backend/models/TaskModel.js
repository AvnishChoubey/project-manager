const mongoose = require("mongoose");
const Project = require("./ProjectModel");

const Schema = mongoose.Schema;

const TaskSchema = new Schema({
    heading: String,
    description: String,
    status: String,
    creationDate: Date,
    expectedETA: Date,
    project: {type: Schema.Types.ObjectId, ref: "Project"}
});

const Task =  mongoose.model("Task", TaskSchema);

export default Task;
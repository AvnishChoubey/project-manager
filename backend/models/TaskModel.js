const mongoose = require("mongoose");
const Stage = require("../constants/TaskType");

const Schema = mongoose.Schema;

const TaskSchema = new Schema({
    heading: String,
    description: String,
    // type: TaskType,
    creationDate: Date,
    expectedETA: Date,
    stage: Stage,
    comments: [{type: Schema.Types.ObjectId, ref: "Comment"}]
});

const TaskModel =  mongoose.model("Task", TaskSchema);

export default TaskModel;
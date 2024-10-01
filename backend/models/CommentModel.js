const mongoose = require("mongoose");
const Task = require("./TaskModel");
const User = require("./UserModel");

const Schema = mongoose.Schema;

const CommentSchema = new Schema({
    userId: {type: Schema.Types.ObjectId, ref: "User"},
    taskId: {type: Schema.Types.ObjectId, ref: "Task"},
    content: {
        type: String,
        required: true
    },
    createdOn: Date
});

const Comment =  mongoose.model("Comment", CommentSchema);
module.exports = Comment;
const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const CommentSchema = new Schema({
    commentedBy: {type: Schema.Types.ObjectId, ref: "User"},
    content: String,
    commentedOn: Date,
    // updatedOn: Date,
    // isEdited: Boolean,
});

module.exports =  mongoose.model("CommentModel", CommentSchema);
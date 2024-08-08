const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const UserSchema = new Schema({
    email: String,
    password: String,
    salt: String,
    tasks: [{type: Schema.Types.ObjectId, ref: "Task"}]
});

const UserModel = mongoose.model("User", UserSchema);

module.exports = UserModel;
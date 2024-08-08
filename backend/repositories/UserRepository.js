const UserModel = require("../models/UserModel");

exports.createUser = async ({email, password, salt}) => {
    const user = new UserModel({
        email,
        password,
        salt
    });
    const result = await user.save();
    return result;
}
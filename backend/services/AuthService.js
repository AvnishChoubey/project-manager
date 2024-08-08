const jwt = require("jssonwebtoken");
const User = require("../models/UserModel");

exports.generateToken = async (user) => {
    const payload = {email: user.email, id: user._id};
    return jwt.sign(payload, SECRET, {expiresIn: "1h"});
};

exports.verifyToken = async (user) => {
    return new Promise((resolve, reject) => {
        jwt.verify(token, SECRET, (error, decoded) => {
            if(error) return reject(err);
            return resolve(decoded);
        })
    })
}
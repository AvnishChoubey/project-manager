const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
require(`dotenv`).config();

const generateSalt = async () => {
    return await bcrypt.genSalt();
};

const generatePassword = async (password,salt) => {
    return await bcrypt.hash(password,salt);
};

const validatePassword = async (givenPassword, savedPassword, salt) => {
    return (await this.generatePassword(givenPassword,salt) === savedPassword);
};

const generateSignature = async (payload) => {
    try {
        return jwt.sign(payload, CLIENT_SECRET, {expiresIn: "1h"});
    } catch (error) {
        console.log(error);
        return error;
    }
};

module.exports = {generateSalt, generatePassword, validatePassword, generateSignature};
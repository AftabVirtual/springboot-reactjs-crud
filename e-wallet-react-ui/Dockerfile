FROM node:v16.16.0 as react-build
WORKDIR /app
COPY package.json ./
RUN npm install
CMD ["npm", "start"]
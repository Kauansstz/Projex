/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",
    "./src/main/resources/static/js/*.js",
    "./src/main/resources/static/css/*.css",
  ],
  safelist: [
    'text-black',
    'bg-black',
    'hover:bg-gray-800'
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

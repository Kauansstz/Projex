/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  safelist: [
    'text-red-500',
    'text-yellow-500',
    'text-green-500'
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

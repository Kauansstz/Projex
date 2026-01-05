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
  extend: {
  animation: {
    fadeIn: 'fadeIn 0.2s ease-out'
  },
  keyframes: {
    fadeIn: {
      '0%': { opacity: 0, transform: 'scale(0.95)' },
      '100%': { opacity: 1, transform: 'scale(1)' }
    }
  }
}

}

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      scrollbar: {
        thumb: {
          borderRadius: '4px', // Bo góc 8px
        },
      },
    },
  },
  plugins: [
    require('tailwind-scrollbar'),
  ],
}

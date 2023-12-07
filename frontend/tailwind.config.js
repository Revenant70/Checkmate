/** @type {import('tailwindcss').Config} */
export default {
  daisyui: {
    themes: [
      {
        mytheme: {
          primary: "#ff0090",

          secondary: "#007e00",

          accent: "#00c8ff",

          neutral: "#000704",

          "base-100": "#3f2314",

          info: "#00adf0",

          success: "#00ed86",

          warning: "#ff7800",

          error: "#ff3248",
        },
      },
    ],
  },
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
};

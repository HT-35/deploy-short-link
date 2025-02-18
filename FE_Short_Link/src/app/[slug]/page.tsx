"use client";
import React from "react";
import { motion } from "framer-motion";
import AnimationText from "@/components/title/AnimationText";

const Redirect = ({ params }: { params: { slug: string } }) => {
  console.log("🚀 ~ Redirect ~ params:", params);
  const box = {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundImage: "url('/img/logo_boostech.png')",
    backgroundPosition: "center",
    backgroundSize: "cover",
  };

  return (
    <div className="flex flex-col gap-14 justify-center items-center mt-32">
      <motion.div
        animate={{
          scale: [1, 2, 2, 1, 1],
          rotate: [0, 0, 180, 180, 0],
          borderRadius: ["0%", "0%", "50%", "50%", "0%"],
        }}
        transition={{
          duration: 2,
          ease: "easeInOut",
          times: [0, 0.2, 0.5, 0.8, 1],
          repeat: Infinity,
          repeatDelay: 1,
        }}
        style={box}
      />
      {/* <h1 className="Loadding text-2xl text-white">Loadding....</h1> */}
      <AnimationText
        title="Loadding...."
        className="text-white text-[30px]"
      ></AnimationText>
    </div>
  );
};

export default Redirect;

"use client";
import React, { useEffect, useState } from "react";
import { motion } from "framer-motion";
import AnimationText from "@/components/title/AnimationText";
import { listAPi } from "@/utils/ListApi/listAPI";
import { sendRequest } from "@/utils/fetchApi";
import Link from "next/link";

const Redirect = ({ params }: { params: { slug: string } }) => {
  const { slug } = params;
  console.log("🚀 ~ Redirect ~ slug:", slug);
  const [error, setError] = useState<boolean>(false);

  useEffect(() => {
    const handleRedirect = async () => {
      const response = await sendRequest<IBackendRes<IGetLink>>({
        url: listAPi.getDetailShortLink(params.slug),
        method: "GET",
      });
      // console.log("🚀 ~ handleRedirect ~ data:", response);
      if (response.statusCode === 200 && response.data) {
        window.location.href = response.data.urlShort;
      } else {
        setError(true);
      }
    };
    handleRedirect();
  }, []);

  const box = {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundImage: "url('/img/logo_boostech.png')",
    backgroundPosition: "center",
    backgroundSize: "cover",
  };

  return (
    <>
      {error ? (
        <div className="flex flex-col gap-14 justify-center items-center mt-10 max-w-[800px] select-none shadow-xl rounded-xl bg-slate-400 w-full h-[500px] mx-auto">
          <h1 className="text-2xl text-red-500 text-center">Link Not Found</h1>
          <Link href="/" className="text-white bg-black px-4 py-2 rounded-md">
            Quay lại trang chủ
          </Link>
        </div>
      ) : (
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
          {/* <h1 className="Loadding text-2xl text-black">Loadding....</h1> */}
          <AnimationText
            title="Redirect...."
            className="text-white text-[30px]"
          ></AnimationText>
        </div>
      )}
    </>
  );
};

export default Redirect;

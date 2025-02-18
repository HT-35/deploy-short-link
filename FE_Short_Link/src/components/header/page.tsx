import React from "react";
import Authen from "../login/Authen";
import Link from "next/link";

const Header = () => {
  return (
    <div className="flex justify-between items-center">
      <Link href="/">
        <div className="text-2xl font-bold bg-gradient-to-r from-pink-500 to-blue-500 text-transparent bg-clip-text select-none">
          Boostech
        </div>
      </Link>

      <div className="auth flex justify-between text-white items-center font-medium gap-10">
        <Authen></Authen>
      </div>
    </div>
  );
};

export default Header;

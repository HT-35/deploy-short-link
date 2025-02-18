"use client";
import React, { useEffect, useState } from "react";
import { Button } from "../ui/button";
import { FormAuthen } from "./FormAuthen";

import Cookies from "js-cookie";

const Authen = () => {
  const [active, setActive] = useState<boolean>(false);

  const [name, setName] = useState<string | undefined>(undefined);

  useEffect(() => {
    const getName = Cookies.get("name");
    setName(getName);
  }, []);

  return (
    <div>
      {name === undefined ? (
        <div className="flex justify-between gap-5 items-center">
          <Button
            className="px-4 py-2 rounded-full  border-[1px] border-[#353C4A] bg-slate-2"
            onClick={() => setActive(true)}
          >
            Login
          </Button>
          <Button
            className="px-5 py-2 rounded-full bg-secondary "
            onClick={() => setActive(true)}
          >
            Register Now
          </Button>
        </div>
      ) : (
        <div className="flex justify-between gap-5 items-center">
          <p>{name}</p>
          <Button
            className="px-5 py-2 rounded-full bg-secondary "
            onClick={() => {
              Cookies.remove("name");
              Cookies.remove("ShortLinkCookie");
              Cookies.remove("accessToken");
              window.location.reload();
            }}
          >
            Logout
          </Button>
        </div>
      )}

      <div
        className={`fixed inset-0 flex justify-center items-center bg-black bg-opacity-50 z-[60] select-none
         transition-all duration-300
          ${
            active
              ? " opacity-100 pointer-events-auto"
              : " opacity-0 pointer-events-none"
          }
        `}
      >
        <FormAuthen setActive={setActive}></FormAuthen>
      </div>
    </div>
  );
};

export default Authen;

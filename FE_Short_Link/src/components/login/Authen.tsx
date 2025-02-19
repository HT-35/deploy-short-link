"use client";
import React, { useEffect, useState } from "react";
import { Button } from "../ui/button";
import { FormAuthen } from "./FormAuthen";

import Cookies from "js-cookie";
import { sendRequest } from "@/utils/fetchApi";
import { listAPi } from "@/utils/ListApi/listAPI";

const Authen = () => {
  const [active, setActive] = useState<boolean>(false);

  const [name, setName] = useState<string | undefined>(undefined);

  useEffect(() => {
    const getName = Cookies.get("name");
    setName(getName);
  }, []);

  const handleLogout = async () => {
    const logout = await sendRequest({
      url: listAPi.logout(),
      method: "GET",
    });
    console.log(logout);
    Cookies.remove("name");
    Cookies.remove("ShortLinkCookie");
    Cookies.remove("accessToken");
    window.location.reload();
  };

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
            onClick={handleLogout}
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

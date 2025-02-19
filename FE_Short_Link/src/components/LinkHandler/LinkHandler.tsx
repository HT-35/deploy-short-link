"use client";
import { faCopy } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@radix-ui/react-tooltip";
import React from "react";
import { Bounce, toast } from "react-toastify";

const LinkHandler = ({
  data,
  OriginalLink = "",
}: {
  data: string;
  OriginalLink?: string;
}) => {
  const CopyClipBoard = () => {
    toast.success(`Copy successfull:  ${data} `, {
      position: "top-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: false,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
      transition: Bounce,
    });

    return navigator.clipboard.writeText(data!);
  };

  return (
    <div className="flex items-center gap-2">
      <TooltipProvider>
        <Tooltip>
          <TooltipTrigger> {OriginalLink || data}</TooltipTrigger>
          <TooltipContent className="bg-white text-black text-sm px-4 py-1 rounded-full font-normal">
            <p> {data || OriginalLink}</p>
          </TooltipContent>
        </Tooltip>
      </TooltipProvider>

      <FontAwesomeIcon
        icon={faCopy}
        className="w-3 cursor-pointer"
        onClick={() => CopyClipBoard()}
      />
    </div>
  );
};

export default LinkHandler;

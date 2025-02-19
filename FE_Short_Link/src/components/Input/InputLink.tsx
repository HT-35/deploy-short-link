"use client";
import React, { useEffect, useState } from "react";
import { Input } from "../ui/input";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLink } from "@fortawesome/free-solid-svg-icons";
import { Button } from "../ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../ui/table";
import LinkHandler from "../LinkHandler/LinkHandler";
import { sendRequest } from "@/utils/fetchApi";
import { listAPi } from "@/utils/ListApi/listAPI";

import { motion } from "framer-motion";

import AnimationText from "../title/AnimationText";
import { Bounce, toast } from "react-toastify";

import Cookies from "js-cookie";

const box = {
  width: 100,
  height: 100,
  borderRadius: 50,
  backgroundImage: "url('/img/logo_boostech.png')",
  backgroundPosition: "center",
  backgroundSize: "cover",
};
const InputLink = () => {
  const [link, setLink] = useState<string>("");

  const [loading, setLoading] = useState<boolean>(false);

  const [listLink, setListLink] = useState<ITypeLink[]>([]);

  useEffect(() => {
    if (typeof window === undefined) return;
    const getToken = Cookies.get("accessToken");
    const getCookie = Cookies.get("ShortLinkCookie");

    const getListLink = async () => {
      let response = null;

      if (getToken !== undefined) {
        response = await sendRequest<IBackendRes<ICreateLink[]>>({
          method: "GET",
          url: listAPi.getAllByEmail(),
          headers: { Authorization: `Bearer ${getToken}` },
        });
        console.log(response);
      } else if (getCookie !== undefined) {
        response = await sendRequest<IBackendRes<ICreateLink[]>>({
          method: "GET",
          url: listAPi.getAllLinkQuickByUUID(),
          useCredentials: true,
        });
        console.log(response);
      }

      if (
        response !== null &&
        response.statusCode == 200 &&
        response.data &&
        response.data.length > 0
      ) {
        const list: ITypeLink[] = (response.data ?? []).map(
          (item: ICreateLink) => {
            const iosString = item.expireDate;
            const date = new Date(iosString || "");
            const formatDate = date.toLocaleDateString("vi-VN");
            return {
              status: "active",
              click: 0,
              shortLink: item.shortLink || "",
              originalLink: item.originalLink || "",
              expireDate: formatDate || "",
            };
          }
        );

        setListLink(list);
      }
    };
    getListLink();
  }, []);

  const handleGetUrl = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLink(e.target.value);
  };

  const handleCreateShortLink = async () => {
    if (!isValidUrlRegex(link)) {
      toast.error("Invalid URL", {
        position: "top-right",
        autoClose: 5000,
      });
      return;
    }

    if (!link.startsWith("http") || link.startsWith("https")) {
      toast.error("Url Missing Protocol Http or Https", {
        position: "top-right",
        autoClose: 5000,
      });
      return;
    }
    setLoading(true);

    const getToken = Cookies.get("accessToken");

    const bodyReq = {
      numberCharacer: 5,
      link: link,
    };

    let response;

    if (getToken !== undefined) {
      response = await sendRequest<IBackendRes<ICreateLink>>({
        method: "POST",
        url: listAPi.createShortLink(),
        body: { ...bodyReq },
        headers: { Authorization: `Bearer ${getToken}` },
      });
    } else {
      response = await sendRequest<IBackendRes<ICreateLink>>({
        method: "POST",
        url: listAPi.createShortLinkQuick(),
        body: { ...bodyReq },
        useCredentials: true,
      });
    }

    await new Promise((resolve) => setTimeout(resolve, 3000));

    if (response.statusCode == 201) {
      const iosString = response.data?.expireDate;
      const date = new Date(iosString || "");
      const formatDate = date.toLocaleDateString("vi-VN");
      const newLink: ITypeLink = {
        status: "active",
        click: 0,
        shortLink: response.data?.shortLink || "",
        originalLink: response.data?.originalLink || "",
        expireDate: formatDate || "",
      };
      setListLink((item) => [...item, newLink]);
      setLink("");
      toast.success("Create Short Link Successful", {
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
    } else {
      const message =
        response.message == "you create 5 short link"
          ? "You have reached the maximum number of short links"
          : "Link already exists or invalid";

      toast.error(message, {
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
    }
    setLoading(false);
  };

  const handleDelete = async (shortLink: string) => {
    const getSlug = shortLink.split("/").pop() || "";
    const getToken = Cookies.get("accessToken");

    let response;
    if (getToken !== undefined) {
      response = await sendRequest<IBackendRes<ICreateLink>>({
        method: "DELETE",
        url: listAPi.deleteLink(getSlug),
        headers: { Authorization: `Bearer ${getToken}` },
      });
    } else {
      response = await sendRequest<IBackendRes<ICreateLink>>({
        method: "DELETE",
        url: listAPi.deleteLinkQuickByUUID(getSlug),
        useCredentials: true,
      });
    }

    if (response.statusCode == 200) {
      setListLink((item) =>
        item.filter((item) => item.shortLink !== shortLink)
      );

      toast.success("Delete Short Link Successful !", {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: false,
      });
    } else {
      toast.error("Delete Short Link Failed !", {
        position: "top-right",
        autoClose: 5000,
      });
    }
  };

  return (
    <>
      {/* Overlay đen */}
      <div
        className={`fixed inset-0 z-10 transition-all duration-300 pointer-events-none  ${
          loading ? "  bg-black opacity-50" : "opacity-0"
        }`}
      ></div>

      {/* Loading Box */}
      <div
        className={`fixed max-w-[1000px] w-full mx-auto h-[500px] rounded-xl z-50 flex flex-col gap-14 justify-center items-center bg-white transition-all duration-300  pointer-events-none ${
          loading ? "opacity-100" : "opacity-0"
        }`}
      >
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
        <AnimationText
          title="Creating Short Link..."
          className="text-[30px] text-black"
        />
      </div>

      {/* Nội dung chính */}
      <div className="input max-w-[660px] w-full relative">
        <Input
          type="text"
          placeholder="Enter the link here"
          className="py-7 rounded-full px-10 pr-36 border-4 border-[#353C4A] text-white"
          value={link}
          onChange={handleGetUrl}
        />
        <div className="absolute top-1/2 -translate-y-1/2 left-3">
          <FontAwesomeIcon icon={faLink} className="w-5 h-5 text-white" />
        </div>
        <div className="absolute top-1/2 -translate-y-1/2 right-[8px]">
          <Button
            className="bg-secondary rounded-full py-6"
            onClick={handleCreateShortLink}
          >
            Shorten Now!
          </Button>
        </div>
      </div>

      <Table className="text-[#C9CED6]">
        <TableHeader className="bg-[#181E29]">
          <TableRow>
            <TableHead className="min-w-[200px] font-bold text-[#C9CED6]">
              Short Links
            </TableHead>
            <TableHead className="min-w-[200px] font-bold text-[#C9CED6]">
              Original Link
            </TableHead>
            <TableHead className="text-[#C9CED6] font-bold text-center">
              Click
            </TableHead>
            <TableHead className="text-[#C9CED6] font-bold text-center">
              Status
            </TableHead>
            <TableHead className="text-center text-[#C9CED6] font-bold min-w-[200px]">
              Expire Date
            </TableHead>
            <TableHead className="text-center text-[#C9CED6] font-bold">
              Delete
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {listLink.map((item, index) => {
            const OriginLink =
              item.originalLink.length > 20
                ? item.originalLink.slice(0, 20).concat("...")
                : item.originalLink;

            return (
              <TableRow key={index} className="border-0">
                <TableCell className="font-thin">
                  <LinkHandler data={item.shortLink} />
                </TableCell>
                <TableCell className="font-thin">
                  <LinkHandler
                    data={item.originalLink}
                    OriginalLink={OriginLink}
                  />
                </TableCell>
                <TableCell className="font-thin text-center">
                  {item.click}
                </TableCell>
                <TableCell className="font-thin text-center">
                  {item.status}
                </TableCell>
                <TableCell className="text-center font-thin ">
                  {item.expireDate}
                </TableCell>
                <TableCell className="text-center font-thin ">
                  <Button
                    className="bg-red-500 text-white rounded-full py-2 px-4"
                    onClick={() => handleDelete(item.shortLink)}
                  >
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </>
  );
};

export default InputLink;

// function isValidUrl(url: string): boolean {
//   try {
//     // Nếu không có giao thức, thêm `https://` vào trước
//     const formattedUrl =
//       url.startsWith("http://") || url.startsWith("https://")
//         ? url
//         : `https://${url}`;
//     console.log("🚀 ~ isValidUrl ~ formattedUrl:", formattedUrl);
//     new URL(formattedUrl);
//     return true;
//   } catch (e) {
//     return false;
//   }
// }

function isValidUrlRegex(url: string): boolean {
  const regex = /^(https?:\/\/)?([a-zA-Z0-9.-]+\.[a-zA-Z]{2,})(\/\S*)?$/;
  return regex.test(url);
}

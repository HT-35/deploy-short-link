import AnimationText from "@/components/title/AnimationText";
import InputLink from "@/components/Input/InputLink";

import ip from "ip";

export default function Home() {
  const ipAddress = ip.address();
  console.log("🚀 ~ Home ~ ipAddress:", ipAddress);

  return (
    <div className="px-10 py-5 ">
      <div className="flex flex-col mt-10 justify-center items-center max-w-[110  0px] mx-auto gap-10">
        <div className="title w-full flex justify-between items-center flex-col gap-5">
          <AnimationText title="Shorten Your Loooong Links :)"></AnimationText>
          <p className="text-lite text-xs select-none">
            Boostech is an efficient and easy-to-use URL shortening service that
            streamlines your online experience.
          </p>
        </div>

        <InputLink></InputLink>
      </div>
    </div>
  );
}

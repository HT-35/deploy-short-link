"use client";
import React, { useLayoutEffect, useRef } from "react";
import gsap from "gsap";
import SplitType from "split-type";

const AnimationText = ({
  title,
  className,
}: {
  title: string;
  className?: string;
}) => {
  const textRef = useRef<HTMLHeadingElement | null>(null);

  useLayoutEffect(() => {
    if (!textRef.current) return;

    const text = new SplitType(textRef.current, { types: "chars" });

    const tl = gsap.timeline({ repeat: -1, repeatDelay: 1 });

    tl.fromTo(
      text.chars,
      { opacity: 0, y: 50 },
      { opacity: 1, y: 0, stagger: 0.05, duration: 0.6, ease: "power2.out" }
    ).to(text.chars, {
      opacity: 0,
      y: -50,
      stagger: 0.05,
      duration: 0.6,
      ease: "power2.in",
      delay: 1,
    });
  }, []);

  return (
    <h1
      ref={textRef}
      className={` select-none  ${
        className ? `${className}` : "text-[60px] text-white"
      }`}
    >
      {title}
    </h1>
  );
};

export default AnimationText;

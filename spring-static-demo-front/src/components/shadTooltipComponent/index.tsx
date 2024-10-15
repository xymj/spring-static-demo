import { ShadToolTipType } from "../../types/components";
import { cn } from "../../utils/utils";
import { Tooltip, TooltipContent, TooltipTrigger } from "../ui/tooltip";

export default function ShadTooltip({
  content,
  side,
  asChild = true,
  children,
  styleClasses,
  delayDuration = 500,
}: ShadToolTipType): JSX.Element {
  return content ? (
    //TooltipPrimitive 显示和隐藏逻辑：自动处理 Tooltip 的显示和隐藏，通常在鼠标悬停或聚焦时显示，在鼠标离开或失去焦点时隐藏。
    <Tooltip defaultOpen={!children} delayDuration={delayDuration}>
      <TooltipTrigger asChild={asChild}>{children}</TooltipTrigger>
      <TooltipContent
        className={cn(styleClasses, "max-w-96")}
        side={side}
        avoidCollisions={false}
        sticky="always"
      >
        {content}
      </TooltipContent>
    </Tooltip>
  ) : (
    <>{children}</>
  );
}

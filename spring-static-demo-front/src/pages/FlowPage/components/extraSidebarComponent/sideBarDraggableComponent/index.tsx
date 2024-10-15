import { DragEventHandler, forwardRef, useRef, useState } from "react";
import IconComponent from "../../../../../components/genericIconComponent";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
} from "../../../../../components/ui/select-custom";
import { useDarkStore } from "../../../../../stores/darkStore";
import useFlowsManagerStore from "../../../../../stores/flowsManagerStore";
import { APIClassType } from "../../../../../types/api";
import {
  createFlowComponent,
  downloadNode,
  getNodeId,
} from "../../../../../utils/reactflowUtils";
import { removeCountFromString } from "../../../../../utils/utils";

// 在 React 中，forwardRef 是一个高阶函数，用于将 ref 从父组件传递到子组件中的特定 DOM 元素或类实例上。这对于需要直接操作子组件内部的 DOM 节点或类实例的情况非常有用。
export const SidebarDraggableComponent = forwardRef(
  (
    {
      sectionName,
      display_name,
      itemName,
      error,
      color,
      onDragStart,
      apiClass,
      official,
    }: {
      sectionName: string;
      apiClass: APIClassType;
      display_name: string;
      itemName: string;
      error: boolean;
      color: string;
      onDragStart: DragEventHandler<HTMLDivElement>;
      official: boolean;
    },
    ref
  ) => {
    const [open, setOpen] = useState(false);
    const deleteComponent = useFlowsManagerStore(
      (state) => state.deleteComponent
    );

    const version = useDarkStore((state) => state.version);
    const [cursorPos, setCursorPos] = useState({ x: 0, y: 0 });
    const popoverRef = useRef<HTMLDivElement>(null);

    const handlePointerDown = (e) => {
      if (!open) {
        const rect = popoverRef.current?.getBoundingClientRect() ?? {
          left: 0,
          top: 0,
        };
        setCursorPos({ x: e.clientX - rect.left, y: e.clientY - rect.top });
      }
    };

    function handleSelectChange(value: string) {
      switch (value) {
        case "share":
          break;
        case "download":
          const type = removeCountFromString(itemName);
          downloadNode(
            createFlowComponent(
              { id: getNodeId(type), type, node: apiClass },
              version
            )
          );
          break;
        case "delete":
          deleteComponent(display_name);
          break;
      }
    }
    return (
      /**electPrimitive 是一个低级别的原始组件，它提供了 Select 组件的核心功能，包括：

          选项渲染：管理和渲染选项列表。
          选择逻辑：处理选项的选择和取消选择。
          键盘导航：支持键盘导航，提升无障碍体验。
          状态管理：管理选中状态和可见状态。
          无障碍支持：确保组件符合无障碍标准，例如使用 ARIA 属性。
      */
      <Select
        onValueChange={handleSelectChange}
        onOpenChange={(change) => setOpen(change)}
        open={open}
        key={itemName}
      >
        <div
          onPointerDown={handlePointerDown}
          onContextMenuCapture={(e) => {
            e.preventDefault();
            setOpen(true);
          }}
          key={itemName}
          data-tooltip-id={itemName}
        >
          {/* 
          
          在 React 中，DragEvent 是一种特殊的事件对象，用于处理拖放（drag and drop）操作。拖放是一种常见的用户交互方式，通常用于文件上传、重新排列项目等场景。DragEvent 提供了一系列的方法和属性，可以帮助你更方便地处理拖放操作。
            主要用途和特性
            1. 拖动事件类型
            React 支持多种拖动事件类型，每种事件类型都有特定的作用和触发时机。常见的拖动事件包括：

            onDragStart: 当用户开始拖动元素时触发。
            onDrag: 当用户正在拖动元素时持续触发。
            onDragEnd: 当拖动操作结束时触发（无论成功还是失败）。
            onDragEnter: 当被拖动的元素进入目标区域时触发。
            onDragOver: 当被拖动的元素在目标区域内移动时持续触发。
            onDragLeave: 当被拖动的元素离开目标区域时触发。
            onDrop: 当被拖动的元素在目标区域内释放时触发。
            2. DragEvent 对象的属性和方法
            DragEvent 对象继承自 MouseEvent，并添加了一些专门用于拖放操作的属性和方法。以下是一些常用的属性和方法：

            dataTransfer: 一个 DataTransfer 对象，用于存储和检索拖放过程中传递的数据。

            setData(type, data): 设置拖放数据的类型和值。
            getData(type): 获取指定类型的数据。
            clearData([type]): 清除指定类型的数据。
            dropEffect: 表示允许的放置效果（none, copy, move, link）。
            effectAllowed: 表示允许的拖动效果（none, copy, copyLink, copyMove, link, linkMove, all, uninitialized）。
            files: 包含被拖动的文件列表（如果是文件拖放）。
            preventDefault(): 阻止浏览器默认行为（例如，在可拖放区域外释放文件时打开文件）。

            示例代码
            以下是一个简单的示例，展示如何在 React 中使用 DragEvent 实现基本的拖放功能：

            JavaScriptReact
            import React from 'react';

            const DraggableItem = ({ children }) => {
              const handleDragStart = (e) => {
                e.dataTransfer.setData('text/plain', children);
              };

              return (
                <div draggable onDragStart={handleDragStart}>
                  {children}
                </div>
              );
            };

            const DropZone = ({ onDrop }) => {
              const handleDragOver = (e) => {
                e.preventDefault();
              };

              const handleDrop = (e) => {
                e.preventDefault();
                const data = e.dataTransfer.getData('text/plain');
                onDrop(data);
              };

              return (
                <div onDragOver={handleDragOver} onDrop={handleDrop}>
                  Drop here
                </div>
              );
            };

            const App = () => {
              const handleDrop = (data) => {
                alert(`Dropped: ${data}`);
              };

              return (
                <div style={{ display: 'flex', gap: '1rem' }}>
                  <DraggableItem>Drag me</DraggableItem>
                  <DropZone onDrop={handleDrop} />
                </div>
              );
            };

            export default App;
            解释
            DraggableItem 组件:

            使用 draggable 属性使元素可拖动。
            在 onDragStart 事件处理器中，使用 e.dataTransfer.setData 方法设置拖放数据。
            DropZone 组件:

            使用 onDragOver 事件处理器阻止浏览器默认行为，以便可以放下元素。
            在 onDrop 事件处理器中，使用 e.dataTransfer.getData 方法获取拖放数据，并调用 onDrop 回调函数。
            App 组件:

            渲染一个可拖动的项和一个放置区。
            当元素被放在放置区内时，弹出提示框显示拖放的数据。
            总结
            DragEvent 是处理拖放操作的重要工具，通过它可以实现丰富的用户交互。 
          */}
          <div
            draggable={!error}
            className={
              "side-bar-components-border bg-background" +
              (error ? " cursor-not-allowed select-none" : "")
            }
            style={{
              borderLeftColor: color,
            }}
            onDragStart={onDragStart}
            onDragEnd={() => {
              document.body.removeChild(
                document.getElementsByClassName("cursor-grabbing")[0]
              );
            }}
          >
            <div
              data-testid={sectionName + display_name}
              id={sectionName + display_name}
              className="side-bar-components-div-form"
            >
              <span className="side-bar-components-text">{display_name}</span>
              <div ref={popoverRef}>
                <IconComponent
                  name="Menu"
                  className="side-bar-components-icon"
                />
                <SelectTrigger></SelectTrigger>
                <SelectContent
                  position="popper"
                  side="bottom"
                  sideOffset={-25}
                  style={{
                    position: "absolute",
                    left: cursorPos.x,
                    top: cursorPos.y,
                  }}
                >
                  <SelectItem value={"download"}>
                    <div className="flex">
                      <IconComponent
                        name="Download"
                        className="relative top-0.5 mr-2 h-4 w-4"
                      />{" "}
                      Download{" "}
                    </div>{" "}
                  </SelectItem>
                  {!official && (
                    <SelectItem value={"delete"}>
                      <div className="flex">
                        <IconComponent
                          name="Trash2"
                          className="relative top-0.5 mr-2 h-4 w-4"
                        />{" "}
                        Delete{" "}
                      </div>{" "}
                    </SelectItem>
                  )}
                </SelectContent>
              </div>
            </div>
          </div>
        </div>
      </Select>
    );
  }
);

export default SidebarDraggableComponent;

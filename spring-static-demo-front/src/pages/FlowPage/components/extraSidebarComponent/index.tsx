import { cloneDeep } from "lodash";
import { LinkIcon, SparklesIcon } from "lucide-react";
import { Fragment, useEffect, useMemo, useState } from "react";
import IconComponent from "../../../../components/genericIconComponent";
import ShadTooltip from "../../../../components/shadTooltipComponent";
import { Input } from "../../../../components/ui/input";
import { Separator } from "../../../../components/ui/separator";
import { PRIORITY_SIDEBAR_ORDER } from "../../../../constants/constants";
import ExportModal from "../../../../modals/exportModal";
import ShareModal from "../../../../modals/shareModal";
import useAlertStore from "../../../../stores/alertStore";
import useFlowStore from "../../../../stores/flowStore";
import useFlowsManagerStore from "../../../../stores/flowsManagerStore";
import { useStoreStore } from "../../../../stores/storeStore";
import { useTypesStore } from "../../../../stores/typesStore";
import { APIClassType, APIObjectType } from "../../../../types/api";
import {
  nodeColors,
  nodeIconsLucide,
  nodeNames,
} from "../../../../utils/styleUtils";
import { classNames, removeCountFromString } from "../../../../utils/utils";
import DisclosureComponent from "../DisclosureComponent";
import ParentDisclosureComponent from "../ParentDisclosureComponent";
import SidebarDraggableComponent from "./sideBarDraggableComponent";
import { sortKeys } from "./utils";
import sensitiveSort from "./utils/sensitive-sort";

export default function ExtraSidebar(): JSX.Element {
  const data = useTypesStore((state) => state.data);
  const templates = useTypesStore((state) => state.templates);
  const getFilterEdge = useFlowStore((state) => state.getFilterEdge);
  const setFilterEdge = useFlowStore((state) => state.setFilterEdge);
  const currentFlow = useFlowsManagerStore((state) => state.currentFlow);
  const hasStore = useStoreStore((state) => state.hasStore);
  const hasApiKey = useStoreStore((state) => state.hasApiKey);
  const validApiKey = useStoreStore((state) => state.validApiKey);

  const setErrorData = useAlertStore((state) => state.setErrorData);
  const [dataFilter, setFilterData] = useState(data);
  const [search, setSearch] = useState("");

  /**
   * 这个函数接收两个参数：一个是 React.DragEvent 对象，另一个是包含拖放数据的对象。以下是代码的详细解释和一些改进建议：

    代码解析
    参数
    event: React.DragEvent<any>: 拖动事件对象。
    data: { type: string; node?: APIClassType }: 自定义数据对象，包含拖放类型和其他相关信息。
    代码逻辑
    克隆当前节点:

    JavaScript
    var crt = event.currentTarget.cloneNode(true);
    克隆当前拖动的目标节点，并保留其所有子节点。
    设置克隆节点的位置和样式:

    JavaScript
    crt.style.position = "absolute";
    crt.style.top = "-500px";
    crt.style.right = "-500px";
    crt.classList.add("cursor-grabbing");
    将克隆节点定位到屏幕不可见的位置（top: -500px; right: -500px;）。
    添加 cursor-grabbing 类，以改变鼠标指针样式。
    将克隆节点添加到文档体:

    JavaScript
    document.body.appendChild(crt);
    将克隆节点添加到文档体中，使其成为页面的一部分。
    设置拖动图像:

    JavaScript
    event.dataTransfer.setDragImage(crt, 0, 0);
    使用克隆节点作为拖动时的视觉反馈图像，并将其位置设置为相对于鼠标指针的偏移量（这里是 (0, 0)）。
    设置拖放数据:

    JavaScript
    event.dataTransfer.setData("nodedata", JSON.stringify(data));
    将自定义数据对象转换为 JSON 字符串，并存储在 dataTransfer 对象中，键名为 "nodedata"。
    改进建议
    清理克隆节点:

    在拖动结束后清除克隆节点，以避免内存泄漏。
    错误处理:

    添加错误处理机制，确保代码健壮性。
    优化样式设置:

    使用 CSS 类来管理样式变化，而不是直接修改内联样式。
    改进后的代码
    JavaScriptReact
    import React from 'react';

    function onDragStart(
      event: React.DragEvent<any>,
      data: { type: string; node?: APIClassType },
    ): void {
      try {
        // 克隆当前节点
        const clonedNode = event.currentTarget.cloneNode(true);

        // 设置克隆节点的位置和样式
        clonedNode.style.position = "absolute";
        clonedNode.style.top = "-500px";
        clonedNode.style.right = "-500px";
        clonedNode.classList.add("cursor-grabbing");

        // 将克隆节点添加到文档体
        document.body.appendChild(clonedNode);

        // 设置拖动图像
        event.dataTransfer.setDragImage(clonedNode, 0, 0);

        // 设置拖放数据
        event.dataTransfer.setData("nodedata", JSON.stringify(data));

        // 注册拖动结束事件处理程序，以清理克隆节点
        event.dataTransfer.effectAllowed = "move"; // 或者 "copy" 等其他效果
        event.dataTransfer.dropEffect = "move"; // 或者 "copy" 等其他效果

        const cleanupClone = () => {
          if (clonedNode.parentNode) {
            clonedNode.parentNode.removeChild(clonedNode);
          }
          document.removeEventListener("dragend", cleanupClone);
        };

        document.addEventListener("dragend", cleanupClone);
      } catch (error) {
        console.error("Error in onDragStart:", error);
      }
    }

    // 示例组件
    const DraggableItem = ({ data, children }) => {
      const handleDragStart = (event) => {
        onDragStart(event, data);
      };

      return (
        <div draggable onDragStart={handleDragStart}>
          {children}
        </div>
      );
    };

    // 示例应用
    const App = () => {
      const data = { type: "ChatInput", node: { id: 1, name: "Chat Input Node" } };

      return (
        <div style={{ display: 'flex', gap: '1rem' }}>
          <DraggableItem data={data}>Drag me</DraggableItem>
          其他组件
        </div>
      );
    };

    export default App;
    解释改进
    清理克隆节点:
    在 dragend 事件中调用 cleanupClone 函数，移除克隆节点并解除事件监听器。
    错误处理:
    使用 try-catch 块捕获潜在的错误，并记录错误信息。
    优化样式设置:

    使用 classList.add 方法添加样式类，而不是直接修改内联样式。

    */
  function onDragStart(
    event: React.DragEvent<any>,
    data: { type: string; node?: APIClassType }
  ): void {
    //start drag event
    var crt = event.currentTarget.cloneNode(true);
    crt.style.position = "absolute";
    crt.style.top = "-500px";
    crt.style.right = "-500px";
    crt.classList.add("cursor-grabbing");
    document.body.appendChild(crt);
    event.dataTransfer.setDragImage(crt, 0, 0);
    event.dataTransfer.setData("nodedata", JSON.stringify(data));
  }

  // Handle showing components after use search input
  function handleSearchInput(e: string) {
    if (e === "") {
      setFilterData(data);
      return;
    }
    setFilterData((_) => {
      let ret = {};
      Object.keys(data).forEach((d: keyof APIObjectType, i) => {
        ret[d] = {};
        let keys = Object.keys(data[d]).filter(
          (nd) =>
            nd.toLowerCase().includes(e.toLowerCase()) ||
            data[d][nd].display_name?.toLowerCase().includes(e.toLowerCase())
        );
        keys.forEach((element) => {
          ret[d][element] = data[d][element];
        });
      });
      return ret;
    });
  }

  useEffect(() => {
    // show components with error on load
    let errors: string[] = [];
    Object.keys(templates).forEach((component) => {
      if (templates[component].error) {
        errors.push(component);
      }
    });
    if (errors.length > 0)
      setErrorData({ title: " Components with errors: ", list: errors });
  }, []);

  function handleBlur() {
    // check if search is search to reset fitler on click input
    if ((!search && search === "") || search === "search") {
      setFilterData(data);
      setFilterEdge([]);
      setSearch("");
    }
  }

  useEffect(() => {
    if (getFilterEdge.length !== 0) {
      setSearch("");
    }

    if (getFilterEdge.length === 0 && search === "") {
      setSearch("");
      setFilterData(data);
    }
  }, [getFilterEdge, data]);

  useEffect(() => {
    handleSearchInput(search);
  }, [data]);

  useEffect(() => {
    if (getFilterEdge?.length > 0) {
      setFilterData((_) => {
        let dataClone = cloneDeep(data);
        let ret = {};
        Object.keys(dataClone).forEach((d: keyof APIObjectType, i) => {
          ret[d] = {};
          if (getFilterEdge.some((x) => x.family === d)) {
            ret[d] = dataClone[d];

            const filtered = getFilterEdge
              .filter((x) => x.family === d)
              .pop()
              .type.split(",");

            for (let i = 0; i < filtered.length; i++) {
              filtered[i] = filtered[i].trimStart();
            }

            if (filtered.some((x) => x !== "")) {
              let keys = Object.keys(dataClone[d]).filter((nd) =>
                filtered.includes(nd)
              );
              Object.keys(dataClone[d]).forEach((element) => {
                if (!keys.includes(element)) {
                  delete ret[d][element];
                }
              });
            }
          }
        });
        setSearch("");
        return ret;
      });
    }
  }, [getFilterEdge]);

  useEffect(() => {
    if (getFilterEdge?.length > 0) {
      setFilterData((_) => {
        let dataClone = cloneDeep(data);
        let ret = {};
        Object.keys(dataClone).forEach((d: keyof APIObjectType, i) => {
          ret[d] = {};
          if (getFilterEdge.some((x) => x.family === d)) {
            ret[d] = dataClone[d];

            const filtered = getFilterEdge
              .filter((x) => x.family === d)
              .pop()
              .type.split(",");

            for (let i = 0; i < filtered.length; i++) {
              filtered[i] = filtered[i].trimStart();
            }

            if (filtered.some((x) => x !== "")) {
              let keys = Object.keys(dataClone[d]).filter((nd) =>
                filtered.includes(nd)
              );
              Object.keys(dataClone[d]).forEach((element) => {
                if (!keys.includes(element)) {
                  delete ret[d][element];
                }
              });
            }
          }
        });
        setSearch("");
        return ret;
      });
    }
  }, [getFilterEdge, data]);

  const ModalMemo = useMemo(
    () => (
      <ShareModal
        is_component={false}
        component={currentFlow!}
        disabled={!hasApiKey || !validApiKey || !hasStore}
      >
        <button
          disabled={!hasApiKey || !validApiKey || !hasStore}
          className={classNames(
            "extra-side-bar-buttons gap-[4px] text-sm font-semibold",
            !hasApiKey || !validApiKey || !hasStore
              ? "button-disable cursor-default text-muted-foreground"
              : ""
          )}
        >
          <IconComponent
            name="Share3"
            className={classNames(
              "-m-0.5 -ml-1 h-6 w-6",
              !hasApiKey || !validApiKey || !hasStore
                ? "extra-side-bar-save-disable"
                : ""
            )}
          />
          Share
        </button>
      </ShareModal>
    ),
    [hasApiKey, validApiKey, currentFlow, hasStore]
  );

  const ExportMemo = useMemo(
    () => (
      <ExportModal>
        <button className={classNames("extra-side-bar-buttons")}>
          <IconComponent name="FileDown" className="side-bar-button-size" />
        </button>
      </ExportModal>
    ),
    []
  );

  const getIcon = useMemo(() => {
    return (SBSectionName: string) => {
      if (nodeIconsLucide[SBSectionName]) {
        return (
          <IconComponent
            name={SBSectionName}
            strokeWidth={1.5}
            className="w-[22px] text-primary"
          />
        );
      }
    };
  }, []);

  return (
    <div className="side-bar-arrangement">
      <div className="side-bar-search-div-placement">
        <Input
          onFocusCapture={() => handleBlur()}
          value={search}
          type="text"
          name="search"
          id="search"
          placeholder="Search"
          className="nopan nodelete nodrag noflow input-search"
          onChange={(event) => {
            handleSearchInput(event.target.value);
            // Set search input state
            setSearch(event.target.value);
          }}
          autocomplete="off"
          readonly="readonly"
          onClick={() =>
            document.getElementById("search").removeAttribute("readonly")
          }
        />
        <div
          className="search-icon"
          onClick={() => {
            if (search) {
              setFilterData(data);
              setSearch("");
            }
          }}
        >
          <IconComponent
            name={search ? "X" : "Search"}
            className={`h-5 w-5 stroke-[1.5] text-primary ${
              search ? "cursor-pointer" : "cursor-default"
            }`}
            aria-hidden="true"
          />
        </div>
      </div>
      <Separator />
      <div className="side-bar-components-div-arrangement">
        <div className="parent-disclosure-arrangement">
          <div className="flex items-center gap-4 align-middle">
            <span className="parent-disclosure-title">Components</span>
          </div>
        </div>
        {Object.keys(dataFilter)
          .sort(sortKeys)
          .filter((x) => PRIORITY_SIDEBAR_ORDER.includes(x))
          .map((SBSectionName: keyof APIObjectType, index) =>
            Object.keys(dataFilter[SBSectionName]).length > 0 ? (
              <DisclosureComponent
                defaultOpen={
                  getFilterEdge.length !== 0 || search.length !== 0
                    ? true
                    : false
                }
                isChild={false}
                key={index + search + JSON.stringify(getFilterEdge)}
                button={{
                  title: nodeNames[SBSectionName] ?? nodeNames.unknown,
                  Icon:
                    nodeIconsLucide[SBSectionName] ?? nodeIconsLucide.unknown,
                }}
              >
                <div className="side-bar-components-gap">
                  {Object.keys(dataFilter[SBSectionName])
                    .sort((a, b) =>
                      sensitiveSort(
                        dataFilter[SBSectionName][a].display_name,
                        dataFilter[SBSectionName][b].display_name
                      )
                    )
                    .map((SBItemName: string, index) => (
                      <ShadTooltip
                        content={
                          dataFilter[SBSectionName][SBItemName].display_name
                        }
                        side="right"
                        key={index}
                      >
                        <SidebarDraggableComponent
                          sectionName={SBSectionName as string}
                          apiClass={dataFilter[SBSectionName][SBItemName]}
                          key={index + SBItemName}
                          onDragStart={(event) =>
                            onDragStart(event, {
                              //split type to remove type in nodes saved with same name removing it's
                              type: removeCountFromString(SBItemName),
                              node: dataFilter[SBSectionName][SBItemName],
                            })
                          }
                          color={nodeColors[SBSectionName]}
                          itemName={SBItemName}
                          //convert error to boolean
                          error={!!dataFilter[SBSectionName][SBItemName].error}
                          display_name={
                            dataFilter[SBSectionName][SBItemName].display_name
                          }
                          official={
                            dataFilter[SBSectionName][SBItemName].official ===
                            false
                              ? false
                              : true
                          }
                        />
                      </ShadTooltip>
                    ))}
                </div>
              </DisclosureComponent>
            ) : (
              <div key={index}></div>
            )
          )}{" "}
        <ParentDisclosureComponent
          defaultOpen={search.length !== 0 || getFilterEdge.length !== 0}
          key={`${search.length !== 0}-${getFilterEdge.length !== 0}-Advanced`}
          button={{
            title: "Experimental",
            Icon: nodeIconsLucide.unknown,
          }}
          testId="extended-disclosure"
        >
          {Object.keys(dataFilter)
            .sort(sortKeys)
            .filter((x) => !PRIORITY_SIDEBAR_ORDER.includes(x))
            .map((SBSectionName: keyof APIObjectType, index) =>
              Object.keys(dataFilter[SBSectionName]).length > 0 ? (
                <Fragment
                  key={`DisclosureComponent${index + search + JSON.stringify(getFilterEdge)}`}
                >
                  <DisclosureComponent
                    isChild={false}
                    defaultOpen={
                      getFilterEdge.length !== 0 || search.length !== 0
                        ? true
                        : false
                    }
                    button={{
                      title: nodeNames[SBSectionName] ?? nodeNames.unknown,
                      Icon:
                        nodeIconsLucide[SBSectionName] ??
                        nodeIconsLucide.unknown,
                    }}
                  >
                    <div className="side-bar-components-gap">
                      {Object.keys(dataFilter[SBSectionName])
                        .sort((a, b) =>
                          sensitiveSort(
                            dataFilter[SBSectionName][a].display_name,
                            dataFilter[SBSectionName][b].display_name
                          )
                        )
                        .map((SBItemName: string, index) => (
                          <ShadTooltip
                            content={
                              dataFilter[SBSectionName][SBItemName].display_name
                            }
                            side="right"
                            key={index}
                          >
                            <SidebarDraggableComponent
                              sectionName={SBSectionName as string}
                              apiClass={dataFilter[SBSectionName][SBItemName]}
                              key={index}
                              onDragStart={(event) =>
                                onDragStart(event, {
                                  //split type to remove type in nodes saved with same name removing it's
                                  type: removeCountFromString(SBItemName),
                                  node: dataFilter[SBSectionName][SBItemName],
                                })
                              }
                              color={nodeColors[SBSectionName]}
                              itemName={SBItemName}
                              //convert error to boolean
                              error={
                                !!dataFilter[SBSectionName][SBItemName].error
                              }
                              display_name={
                                dataFilter[SBSectionName][SBItemName]
                                  .display_name
                              }
                              official={
                                dataFilter[SBSectionName][SBItemName]
                                  .official === false
                                  ? false
                                  : true
                              }
                            />
                          </ShadTooltip>
                        ))}
                    </div>
                  </DisclosureComponent>
                  {index ===
                    Object.keys(dataFilter).length -
                      PRIORITY_SIDEBAR_ORDER.length +
                      1 && (
                    <>
                      <a
                        target={"_blank"}
                        href="https://langflow.store"
                        className="components-disclosure-arrangement"
                      >
                        <div className="flex gap-4">
                          {/* BUG ON THIS ICON */}
                          <SparklesIcon
                            strokeWidth={1.5}
                            className="w-[22px] text-primary"
                          />

                          <span className="components-disclosure-title">
                            Discover More
                          </span>
                        </div>
                        <div className="components-disclosure-div">
                          <div>
                            <LinkIcon className="h-4 w-4 text-foreground" />
                          </div>
                        </div>
                      </a>
                    </>
                  )}
                </Fragment>
              ) : (
                <div key={index}></div>
              )
            )}
        </ParentDisclosureComponent>
      </div>
    </div>
  );
}

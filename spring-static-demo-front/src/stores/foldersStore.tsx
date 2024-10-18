import { create } from "zustand";
import { DEFAULT_FOLDER, STARTER_FOLDER_NAME } from "../constants/constants";
import {
  getFolderById,
  getFolders,
  uploadFlowsFromFolders,
} from "../pages/MainPage/services";
import { FoldersStoreType } from "../types/zustand/folders";
import useFlowsManagerStore from "./flowsManagerStore";
import { useTypesStore } from "./typesStore";

export const useFolderStore = create<FoldersStoreType>((set, get) => ({
  folders: [],
  getFoldersApi: (refetch = false, startupApplication: boolean = false) => {
    console.log("getFoldersApi start");
    return new Promise<void>((resolve, reject) => {
      get().setIsLoadingFolders(true);
      if (get()?.folders.length === 0 || refetch === true) {
        console.log("getFolders start");
        getFolders().then(
          async (res) => {
            console.log(`getFolders res ${JSON.stringify(res)}`);
            const foldersWithoutStarterProjects = res?.filter(
              (folder) => folder.name !== STARTER_FOLDER_NAME
            );
            console.log(
              `getFolders foldersWithoutStarterProjects ${JSON.stringify(foldersWithoutStarterProjects)}`
            );

            const starterProjects = res?.find(
              (folder) => folder.name === STARTER_FOLDER_NAME
            );
            console.log(
              `getFolders starterProjects ${JSON.stringify(starterProjects)}`
            );

            set({ starterProjectId: starterProjects!.id ?? "" });
            set({ folders: foldersWithoutStarterProjects });

            const myCollectionId = res?.find(
              (f) => f.name === DEFAULT_FOLDER
            )?.id;

            console.log(
              `getFolders myCollectionId ${JSON.stringify(myCollectionId)}`
            );
            set({ myCollectionId });

            console.log("refreshFlows call");
            const { refreshFlows } = useFlowsManagerStore.getState();
            const { getTypes } = useTypesStore.getState();

            console.log("getFolders refetch :", refetch);
            if (refetch) {
              if (startupApplication) {
                console.log("refreshFlows call");
                await refreshFlows();
                console.log("refreshFlows end");
                console.log("getTypes call");
                await getTypes();
                console.log("getTypes end");
                get().setIsLoadingFolders(false);
                console.log("setIsLoadingFolders false");
              } else {
                console.log("refreshFlows call else");
                refreshFlows();
                console.log("refreshFlows end else");
                console.log("getTypes call else");
                getTypes();
                console.log("getTypes end else");
                get().setIsLoadingFolders(false);
                console.log("setIsLoadingFolders false");
              }
            }

            resolve();
          },
          (error) => {
            set({ folders: [] });
            get().setIsLoadingFolders(false);
            reject(error);
          }
        );
      }
    });
  },
  refreshFolders: () => {
    return new Promise<void>((resolve, reject) => {
      getFolders().then(
        async (res) => {
          const foldersWithoutStarterProjects = res?.filter(
            (folder) => folder.name !== STARTER_FOLDER_NAME
          );

          const starterProjects = res?.find(
            (folder) => folder.name === STARTER_FOLDER_NAME
          );

          set({ starterProjectId: starterProjects!.id ?? "" });
          set({ folders: foldersWithoutStarterProjects });

          const myCollectionId = res?.find(
            (f) => f.name === DEFAULT_FOLDER
          )?.id;

          set({ myCollectionId });

          resolve();
        },
        (error) => {
          set({ folders: [] });
          get().setIsLoadingFolders(false);
          reject(error);
        }
      );
    });
  },
  setFolders: (folders) => set(() => ({ folders: folders })),
  isLoadingFolders: false,
  setIsLoadingFolders: (isLoadingFolders) => set(() => ({ isLoadingFolders })),
  getFolderById: (id) => {
    if (id) {
      getFolderById(id).then((res) => {
        const setAllFlows = useFlowsManagerStore.getState().setAllFlows;
        setAllFlows(res?.flows);
        set({ selectedFolder: res });
      });
    }
  },
  selectedFolder: null,
  setSelectedFolder: (folder) => set(() => ({ selectedFolder: folder })),
  loadingById: false,
  getMyCollectionFolder: () => {
    const folders = get().folders;
    const myCollectionId = folders?.find((f) => f.name === DEFAULT_FOLDER)?.id;
    if (myCollectionId) {
      getFolderById(myCollectionId).then((res) => {
        set({ myCollectionFlows: res });
      });
    }
  },
  setMyCollectionFlow: (folder) => set(() => ({ myCollectionFlows: folder })),
  myCollectionFlows: null,
  setMyCollectionId: () => {
    const folders = get().folders;
    const myCollectionId = folders?.find((f) => f.name === DEFAULT_FOLDER)?.id;
    if (myCollectionId) {
      set({ myCollectionId });
    }
  },
  myCollectionId: "",
  folderToEdit: null,
  setFolderToEdit: (folder) => set(() => ({ folderToEdit: folder })),
  folderUrl: "",
  setFolderUrl: (url) => set(() => ({ folderUrl: url })),
  folderDragging: false,
  setFolderDragging: (folder) => set(() => ({ folderDragging: folder })),
  folderIdDragging: "",
  setFolderIdDragging: (id) => set(() => ({ folderIdDragging: id })),
  uploadFolder: () => {
    return new Promise<void>((resolve, reject) => {
      const input = document.createElement("input");
      input.type = "file";
      input.accept = ".json";
      input.onchange = (event: Event) => {
        if (
          (event.target as HTMLInputElement).files![0].type ===
          "application/json"
        ) {
          const file = (event.target as HTMLInputElement).files![0];
          const formData = new FormData();
          formData.append("file", file);
          file.text().then((text) => {
            const data = JSON.parse(text);
            if (data.data?.nodes) {
              useFlowsManagerStore
                .getState()
                .addFlow(true, data)
                .then(() => {
                  resolve();
                })
                .catch((error) => {
                  reject(error);
                });
            } else {
              uploadFlowsFromFolders(formData)
                .then(() => {
                  get()
                    .getFoldersApi(true)
                    .then(() => {
                      resolve();
                    });
                })
                .catch((error) => {
                  reject(error);
                });
            }
          });
        }
      };
      input.click();
    });
  },
  starterProjectId: "",
  setStarterProjectId: (id) => set(() => ({ starterProjectId: id })),
}));

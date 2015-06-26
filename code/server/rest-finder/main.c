#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

int main()
{
    HWND hWnd = FindWindow("Notepad", NULL);
    HMENU hMenu = GetMenu(hWnd);
    int nCount = GetMenuItemCount(hMenu);

    //wchar_t wzBuf[512] = {0}; // буфер для имени
    char wzBuf[512] = {0}; // буфер для имени
    MENUITEMINFOA Info = {0};
    Info.cbSize = sizeof(MENUITEMINFOA);
    Info.fMask = MIIM_STRING;
    Info.dwTypeData = wzBuf; // сюда копируется имя
    Info.cch = sizeof(wzBuf);
    // как ни странно, если вторым параметром поставить 0, то функция вернет пустую строку
    BOOL bRes = GetMenuItemInfo(hMenu, 2, FALSE, &Info);

    HMENU hM = GetSubMenu(hMenu, 2);
    bRes = GetMenuItemInfo(hM, 1, TRUE, &Info);
    HMENU NPadSubMenu = GetSubMenu(hMenu, 1);
    SendMessage(hWnd, WM_COMMAND, GetMenuItemID(NPadSubMenu, 10), 0);
    PostMessage(hWnd, WM_COMMAND, GetMenuItemID(NPadSubMenu, 9), 0);

    //bRes = GetMenuItemInfo(hMenu, 2, TRUE, &Info);
    //bRes = GetMenuItemInfo(hMenu, 3, TRUE, &Info);

    //PostMessage(hWnd, WM_COMMAND, 2, 0);
    //PostMessage(hWnd, WM_COMMAND, 4, 0);

    printf("Hello world!\n");
    return 0;
}

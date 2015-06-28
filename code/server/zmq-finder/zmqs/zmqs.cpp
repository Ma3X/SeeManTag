//#include <stdio.h>

#define _WIN32_WINNT 0x0501
//#pragma comment(lib, "libws2_32.a")
#define ZMQ_STATIC
#include "zmq.h"

#include <iostream>
#include <string>

#include <windows.h>
#include <tlhelp32.h>

#define sleep(x) Sleep(1000 * x)

// http://www.cplusplus.com/forum/windows/12137/
//DWORD FindProcessId(const std::wstring& processName)
DWORD FindProcessId(const std::string& processName)
{
	PROCESSENTRY32 processInfo;
	processInfo.dwSize = sizeof(processInfo);

	HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
	if ( processesSnapshot == INVALID_HANDLE_VALUE )
		return 0;

	Process32First(processesSnapshot, &processInfo);
	if ( !processName.compare(processInfo.szExeFile) )
	{
		CloseHandle(processesSnapshot);
		return processInfo.th32ProcessID;
	}

	while ( Process32Next(processesSnapshot, &processInfo) )
	{
		if ( !processName.compare(processInfo.szExeFile) )
		{
			CloseHandle(processesSnapshot);
			return processInfo.th32ProcessID;
		}
	}

	CloseHandle(processesSnapshot);
	return 0;
}

HWND GetProcessWindow(DWORD dwProcessId)
{
    HWND hwnd = NULL;
    HWND tmp = GetDesktopWindow();
    tmp = GetWindow( tmp, GW_CHILD );
    if( tmp ){
        bool flag = false;
        while( !flag ){
            tmp = GetWindow( tmp, GW_HWNDNEXT );
            if( tmp ){
                DWORD dwProcId = 0;
                DWORD ThreadId = GetWindowThreadProcessId(tmp, &dwProcId);
                if(dwProcId == dwProcessId){
                    flag = true;
                    hwnd = tmp;
                }
            } else flag = true;
        }
    }
    return hwnd;
}

/*
int main__ (int argc, char const *argv[])
{
    std::vector<DWORD> SetOfPID;
    GetProcessID("Rundll32",SetOfPID);
     // get all process id's of Rundll32

    if (SetOfPID.empty())   // Process is not running
    {
        printf("Process is not running\n");
    }
    else    // Process is running
    {
        for (int i=0;i < SetOfPID.size(); i++)
        {
            printf("Process ID is %d\n", SetOfPID[i]);
            HANDLE hProcess = OpenProcess(
                 PROCESS_ALL_ACCESS,FALSE,SetOfPID[i]);
            // some code...
            CloseHandle(hProcess);
        }
    }
}
*/

//namespace std::string;
HWND g_hW = NULL;
HWND g_hWnd = NULL;
std::size_t found = std::string::npos;
BOOL CALLBACK MyEnumWindowsProc(HWND hwnd, LPARAM lparam)
{
    int& i = *(reinterpret_cast<int*>(lparam));
    int ii = *(reinterpret_cast<int*>(lparam));
    //++i;
    char title[256];
    ::GetWindowText(hwnd, title, sizeof(title));
    //::printf("Window #%d (%x): %s\n", i, hwnd, title);
    std::string tit(title);
    //std::size_t found = tit.find_first_of("ContaCam");
    if(ii) {
        found = tit.find("Logitech");
    } else {
        found = tit.find("ContaCam");
    }
    if(std::string::npos != found && found == 0) {
        ::printf("Window #%d (%x): %s\n", i, hwnd, title);
      if(ii) {
        g_hWnd = hwnd;
      } else {
        g_hW = hwnd;
      }
      return FALSE;
    }
    return TRUE;
}

int main_start()
{
    //HWND hWnd = FindWindow("Notepad", NULL);
    HWND hWnd = g_hW;
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
    HMENU NPadSubMenu = GetSubMenu(hMenu, 2);
    //SendMessage(hWnd, WM_COMMAND, GetMenuItemID(NPadSubMenu, 10), 0);
    //PostMessage(hWnd, WM_COMMAND, GetMenuItemID(NPadSubMenu, 9), 0);
    PostMessage(hWnd, WM_COMMAND, GetMenuItemID(NPadSubMenu, 0), 0);

    //bRes = GetMenuItemInfo(hMenu, 2, TRUE, &Info);
    //bRes = GetMenuItemInfo(hMenu, 3, TRUE, &Info);

    //PostMessage(hWnd, WM_COMMAND, 2, 0);
    //PostMessage(hWnd, WM_COMMAND, 4, 0);

    printf("Hello world!\n");
    return 0;
}

int main_check ()
{
    int i = 0;
    g_hW == NULL;
    g_hWnd == NULL;
    ::printf("Starting EnumWindows()\n");
    ::EnumWindows(&MyEnumWindowsProc, reinterpret_cast<LPARAM>(&i));
    if(g_hW != NULL) {
        i++;
        ::EnumChildWindows(g_hW, &MyEnumWindowsProc, reinterpret_cast<LPARAM>(&i));
        if(g_hWnd != NULL) {
            ::printf("Logitech is running\n");
            i++;
        }
    }
    ::printf("EnumWindows() ended\n");
	//system("PAUSE");
    return i;
}

int main (int argc, char const *argv[])
{
    int ret = main_check();
    switch(ret) {
        case 1:
            main_start();
            break;
        case 2:
            PostMessage(g_hWnd, WM_CLOSE, 0, 0);
            break;
        case 0:
        default:
            ::printf("Main ContaCam window not found\n");
            break;
    }
    return 0;
}

int main___ (int argc, char const *argv[])
{
    //printf("Starting...\n");

	std::string processName;

	std::cout << "Enter the process name: ";
	std::getline(std::cin, processName);

	DWORD processID = FindProcessId(processName);

	if ( processID == 0 )
		std::cout << "Could not find " << processName.c_str() << std::endl;
	else {
		std::cout << "Process ID is " << processID << std::endl;
		//HWND hW = GetProcessWindow(processID);
		HWND hW = ::GetTopWindow(0 );
		while ( hW )
		{
		  DWORD pid;
		  DWORD dwTheardId = ::GetWindowThreadProcessId( hW,&pid);
		  if ( pid == processID /*your process id*/ )
          {
              // here h is the handle to the window
              DWORD a = 0x30a76;
              if((DWORD)hW != a) {
                DWORD b = 0x30a68;
                if((DWORD)hW != b) {
                  dwTheardId++;
                  break;
                }
              }
          }
          hW = ::GetNextWindow( hW , GW_HWNDNEXT);
		}
		std::cout << "HWND is " << hW << std::endl;
	}

	system("PAUSE");

    return 0;
}

int main_ (int argc, char const *argv[])
{
    int major, minor, patch;
    zmq_version(&major, &minor, &patch);
    printf("Installed ZeroMQ version: %d.%d.%d\n", major, minor, patch);

    void* context = zmq_ctx_new();
    void* respond = zmq_socket(context, ZMQ_REP);

    zmq_bind(respond, "tcp://*:3333");
    printf("Starting...\n");

    for(;;)
    {
        zmq_msg_t request;
        zmq_msg_init(&request);
        int res = zmq_msg_recv(&request, respond, 0);
        if(res == -1) break;

        //-unsigned int type = request[0];
        //quint8 type = request.[0];
        //res = zmq_getsockopt(respond, ZMQ_RCVMORE, &more, &more_size);
        //if(!more) return false;

        char *data;
        int data_size;

        data = (char*) zmq_msg_data(&request);
        data_size = zmq_msg_size(&request);
        data[data_size] = '\0';

        //zmq_msg_t part;
        //switch(type) {
        //    case ZMQ_TYPE_STRING:
        //        zmq_msg_init(&part);
        //        zmq_msg_recv(&part, sub, 0);
        //        data = (char*) zmq_msg_data(&part);
        //        data_size = zmq_msg_size(&part);
        //        data[data_size] = '\0';
                //str = QString(data);
        //        zmq_msg_close(&part);
        //        break;
            //case ZMQ_TYPE_DATA:
            //    zmq_msg_init(&part);
            //    zmq_msg_recv(&part, sub, 0);
            //    data = (char*) zmq_msg_data(&part);
            //    data_size = zmq_msg_size(&part);
            //    ba = QByteArray(data, data_size);
            //    if(ba.size() < 32) qDebug() << "DATA:" << ba.toHex() << "size=" << data_size;
            //    else qDebug() << "DATA: LARGE" << "size=" << data_size; zmq_msg_close(&part); break;
        //}

        printf("Received: %s\n", data);
        zmq_msg_close(&request);
        sleep(1); // sleep one second
        zmq_msg_t reply;
        zmq_msg_init_size(&reply, strlen("world"));
        memcpy(zmq_msg_data(&reply), "world", 5);
        zmq_msg_send(&reply, respond, 0);
        zmq_msg_close(&reply);
    }
    zmq_close(respond);
    zmq_ctx_destroy(context);

    return 0;
}

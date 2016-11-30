       IDENTIFICATION DIVISION.
       PROGRAM-ID. MAINHRMS.

         DATA DIVISION.
         WORKING-STORAGE SECTION.
         77 CHOICE PIC 9.

         SCREEN SECTION.
         01 ERASE-1.
            02 BLANK SCREEN.

         PROCEDURE DIVISION.
         MAIN-PARA.
             DISPLAY ERASE-1
             DISPLAY "*******************************************"
               AT LINE 3 COL 15.
             DISPLAY "     HUMAN RESOURCE MANAGEMENT SYSTEM      "
               AT LINE 5 COL 15.
             DISPLAY "*******************************************"
               AT LINE 7 COL 15.
             DISPLAY "1. HRMS WRITE" AT LINE 10 COL 25.
             DISPLAY "2. HRMS READ" AT LINE 12 COL 25.
             DISPLAY "3. EXIT" AT LINE 14 COL 25.
             DISPLAY "ENTER YOUR CHOICE :" AT LINE 16 COL 25.
             ACCEPT CHOICE AT LINE 16 COL 46.
             EVALUATE CHOICE
                WHEN 1
                    CALL "EMPWRITE"
                    CANCEL "EMPWRITE"
                    GO TO MAIN-PARA
                WHEN 2
                    CALL "EMPREAD"
                    CANCEL "EMPREAD"
                    GO TO MAIN-PARA
                WHEN OTHER
                    STOP RUN.
       END PROGRAM MAINHRMS.

       IDENTIFICATION DIVISION.
       PROGRAM-ID. EMPREAD.

         ENVIRONMENT DIVISION.
         INPUT-OUTPUT SECTION.
         FILE-CONTROL.
             SELECT EMPFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS EEMPID
                 FILE STATUS IS FSE.

             SELECT LEAVEFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS LEMPID
                 FILE STATUS IS FSL.

             SELECT BRANCHFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS BBRID
                 FILE STATUS IS FSB.

             SELECT DESIGNATIONFILE ASSIGN TO DISK
                 ORGANIZATION IS SEQUENTIAL
                 ACCESS MODE IS SEQUENTIAL
                 FILE STATUS IS FSDES.

             SELECT DEPARTMENTFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS DEPCODE
                 FILE STATUS IS FSDEP.

             SELECT REVISIONFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS RREVID
                 ALTERNATE RECORD KEY IS REMPID
                 FILE STATUS IS FSR.

             SELECT PAYMENTFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS PEMPID
                 FILE STATUS IS FSP.

             SELECT CONFIRMATIONFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS CCONID
                 ALTERNATE RECORD KEY IS CEMPID
                 FILE STATUS IS FSC.

             SELECT GRADEFILE ASSIGN TO DISK
                 ORGANIZATION IS SEQUENTIAL
                 ACCESS MODE IS SEQUENTIAL
                 FILE STATUS IS FSG.

             SELECT TRANSFERFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS TTRFID
                 FILE STATUS IS FST.

             SELECT EMPPERSONALFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS EPEMPID
                 FILE STATUS IS FSEP.

         DATA DIVISION.
         FILE SECTION.
         FD EMPFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "EMP.DAT".
         01 EMPREC.
             02 EEMPID    PIC X(6).
             02 EEMPNAME  PIC X(25).
             02 EEMPADDR  PIC X(30).
             02 EPHONE    PIC X(10).
             02 EDOJ      PIC X(10).
             02 EDIP      PIC X(10).
             02 EUG       PIC X(4).
             02 EPG       PIC X(4).
             02 EPROFQ    PIC X(4).
             02 ESKILL    PIC X(10).
             02 EGRDNO    PIC 99.
             02 EBRNID    PIC X(6).
             02 EDESID    PIC X(6).

         FD LEAVEFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "LEAVE.DAT".
         01 LEAVEREC.
             02 LEMPID    PIC X(6).
             02 LFMDATE   PIC X(10).
             02 LTODATE   PIC X(10).
             02 LLEVCAT   PIC X(3).

         FD BRANCHFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "BRANCH.DAT".
         01 BRANCHREC.
             02 BBRID    PIC X(6).
             02 BBRNAME  PIC X(15).
             02 BBRADD   PIC X(30).
             02 BBRPH    PIC X(10).
             02 BEMAIL   PIC X(20).
             02 BMGRNAME PIC X(25).

         FD DESIGNATIONFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "DESIG.DAT".
         01 DESIGNATIONREC.
             02 DESID    PIC X(6).
             02 DESIGN   PIC X(15).
             02 DESHRT   PIC X(4).

         FD DEPARTMENTFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "DEPART.DAT".
         01 DEPARTMENTREC.
             02 DEPCODE  PIC X(6).
             02 DEPNAME  PIC X(20).

         FD REVISIONFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "REVISION.DAT".
         01 REVISIONREC.
             02 RREVID   PIC X(6).
             02 REMPID   PIC X(6).
             02 RDESCODE PIC X(6).
             02 RBASIC   PIC 9(6)V99.
             02 RHRA     PIC 9(6)V99.
             02 RDPA     PIC 9(6)V99.
             02 RPPA     PIC 9(6)V99.
             02 REDUA    PIC 9(6)V99.
             02 RTECHJR  PIC 9(6)V99.
             02 RLUNCHA  PIC 9(6)V99.
             02 RCONVEY  PIC 9(6)V99.
             02 RBUSATR  PIC 9(6)V99.
             02 RLTA     PIC 9(6)V99.
             02 RPF      PIC 9(6)V99.
             02 RESI     PIC 9(6)V99.
             02 RREVDATE PIC X(10).

         FD PAYMENTFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "PAYMENT.DAT".
         01 PAYMENTREC.
             02 PEMPID   PIC X(6).
             02 PBASIC   PIC 9(6)V99.
             02 PDA      PIC 9(6)V99.
             02 PCCA     PIC 9(6)V99.
             02 PHRA     PIC 9(6)V99.
             02 PDPA     PIC 9(6)V99.
             02 PPPA     PIC 9(6)V99.
             02 PEDUA    PIC 9(6)V99.
             02 PTECHJR  PIC 9(6)V99.
             02 PLUNCHA  PIC 9(6)V99.
             02 PCONVEY  PIC 9(6)V99.
             02 PBUSATR  PIC 9(6)V99.
             02 PLTA     PIC 9(6)V99.
             02 PPF      PIC 9(6)V99.
             02 PESI     PIC 9(6)V99.
             02 PGRTY    PIC 9(6)V99.
             02 PPTAX    PIC 9(6)V99.
             02 PITAX    PIC 9(6)V99.
             02 PLOAN    PIC 9(8)V99.
             02 PLOANDA  PIC 9(8)V99.
             02 POTHERD  PIC 9(6)V99.
             02 PPERINC  PIC 9(6)V99.
             02 PMEDI    PIC 9(6)V99.
             02 PBOOK    PIC 9(6)V99.
             02 PENTER   PIC 9(6)V99.
             02 PTPH     PIC 9(6)V99.
             02 PHOUSE   PIC 9(6)V99.
             02 PVEHMAN  PIC 9(6)V99.
             02 PCREDIT  PIC 9(6)V99.
             02 PCLUB    PIC 9(6)V99.
             02 PCL      PIC 99.
             02 PSL      PIC 99.
             02 PPL      PIC 99.
             02 PLLOP    PIC 999.
             02 POTHERL  PIC 999.

         FD CONFIRMATIONFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "CONFIRM.DAT".
         01 CONFIRMATIONREC.
             02 CCONID   PIC X(6).
             02 CEMPID   PIC X(6).
             02 CCDATE   PIC X(6).

         FD GRADEFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "GRADE.DAT".
         01 GRADEREC.
             02 GGRADE   PIC 99.
             02 GDESIGN  PIC X(25).

         FD TRANSFERFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "TRANSFER.DAT".
         01 TRANSFERREC.
             02 TTRFID   PIC X(6).
             02 TEMPID   PIC X(6).
             02 TOBRID   PIC X(6).
             02 TTRFDT   PIC X(10).

         FD EMPPERSONALFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "EMPPER.DAT".
         01 EMPPERSONALREC.
             02 EPEMPID  PIC X(6).
             02 EPTADD   PIC X(30).
             02 EPTPH    PIC X(10).
             02 EPDOB    PIC X(10).
             02 EPPOB    PIC X(10).
             02 EPLANG   PIC X(15).
             02 EPBLOOD  PIC X(4).
             02 EPWEIGHT PIC 999.
             02 EPHEIGHT PIC 999.
             02 EPVISION PIC X(15).
             02 EPFATHER PIC X(25).
             02 EPDOBF   PIC X(10).
             02 EPMOTHER PIC X(25).
             02 EPDOBM   PIC X(10).
             02 EPSPOUSE PIC X(25).
             02 EPCHILD  PIC X(25).
             02 EPDOBC   PIC X(10).

         WORKING-STORAGE SECTION.
         77 FSE   PIC XX.
         77 FSL   PIC XX.
         77 FSB   PIC XX.
         77 FSDES PIC XX.
         77 FSDEP PIC XX.
         77 FSR   PIC XX.
         77 FSP   PIC XX.
         77 FSC   PIC XX.
         77 FSG   PIC XX.
         77 FST   PIC XX.
         77 FSEP  PIC XX.
         77 DES   PIC X(6).
         77 GR    PIC 99.
         77 CHOICE PIC 99.
         77 OMIT  PIC 99.
         77 LBCITY PIC X(30).
         77 LBLINE PIC 99.

         SCREEN SECTION.
         01 ERASE-1.
            02 BLANK SCREEN.

         PROCEDURE DIVISION.
         MAIN-PARA.
             DISPLAY ERASE-1
             DISPLAY "*******************************************"
               AT LINE 3 COL 10.
             DISPLAY "     HUMAN RESOURCE MANAGEMENT SYSTEM      "
               AT LINE 5 COL 10.
             DISPLAY "*******************************************"
               AT LINE 7 COL 10.
             DISPLAY "01. EMPLOYEE FILE" AT LINE 11 COL 5.
             DISPLAY "02. LEAVE FILE" AT LINE 12 COL 5.
             DISPLAY "03. LIST BRANCH FILE" AT LINE 13 COL 5.
             DISPLAY "04. BRANCH FILE" AT LINE 14 COL 5.
             DISPLAY "05. DESIGNATION FILE" AT LINE 15 COL 5.
             DISPLAY "06. DEPARTMENT FILE" AT LINE 16 COL 5.
             DISPLAY "07. REVISION FILE" AT LINE 17 COL 5.
             DISPLAY "08. PAYMENT FILE" AT LINE 18 COL 5.
             DISPLAY "09. CONFIRMATION FILE" AT LINE 19 COL 5.
             DISPLAY "10. GRADE FILE" AT LINE 20 COL 5.
             DISPLAY "11. TRANSFER FILE" AT LINE 21 COL 5.
             DISPLAY "12. EMPLOYEE PERSONAL FILE" AT LINE 22 COL 5.
             DISPLAY "13. EXIT" AT LINE 23 COL 5.
             DISPLAY "ENTER U R CHOICE :" AT LINE 24 COL 25.
             ACCEPT CHOICE AT LINE 24 COL 45.
             EVALUATE CHOICE
                WHEN 1     GO TO EMP-PARA
                WHEN 2     GO TO LEAVE-PARA
                WHEN 3     GO TO LIST-BRANCH-PARA
                WHEN 4     GO TO BRANCH-PARA
                WHEN 5     GO TO DESIGNATION-PARA
                WHEN 6     GO TO DEPARTMENT-PARA
                WHEN 7     GO TO REVISION-PARA
                WHEN 8     GO TO PAYMENT-PARA
                WHEN 9     GO TO CONFIRMATION-PARA
                WHEN 10     GO TO GRADE-PARA
                WHEN 11    GO TO TRANSFER-PARA
                WHEN 12    GO TO EMPPERSONAL-PARA
                WHEN OTHER EXIT PROGRAM.

         EMP-PARA.
             DISPLAY ERASE-1
             OPEN INPUT EMPFILE.
             DISPLAY "ENTER CODE :" AT LINE 23 COL 25.
             ACCEPT EEMPID AT LINE 23 COL 38.
             DISPLAY ERASE-1
             READ EMPFILE INVALID KEY GO TO ERROR-EMP-PARA.
             DISPLAY " CODE                 : " AT LINE 1 COL 1.
             DISPLAY EEMPID AT LINE 1 COL 25.
             DISPLAY " NAME                 : " AT LINE 2 COL 1.
             DISPLAY EEMPNAME AT LINE 2 COL 25.
             DISPLAY " ADDRESS              : " AT LINE 3 COL 1.
             DISPLAY EEMPADDR AT LINE 3 COL 25.
             DISPLAY " PHONE                : " AT LINE 4 COL 1.
             DISPLAY EPHONE AT LINE 4 COL 25.
             DISPLAY " DATE OF JOIN         : " AT LINE 5 COL 1.
             DISPLAY EDOJ AT LINE 5 COL 25.
             DISPLAY " DIPLOMA              : " AT LINE 6 COL 1.
             DISPLAY EDIP AT LINE 6 COL 25.
             DISPLAY " UG                   : " AT LINE 7 COL 1.
             DISPLAY EUG AT LINE 7 COL 25.
             DISPLAY " PG                   : " AT LINE 8 COL 1.
             DISPLAY EPG AT LINE 8 COL 25.
             DISPLAY " PROFESSIONAL QUALITY : " AT LINE 9 COL 1.
             DISPLAY EPROFQ AT LINE 9 COL 25.
             DISPLAY " SKILL SET            : " AT LINE 10 COL 1.
             DISPLAY ESKILL AT LINE 10 COL 25.
             DISPLAY " GRADE NUMBER         : " AT LINE 11 COL 1.
             DISPLAY EGRDNO AT LINE 11 COL 25.
             DISPLAY " BRANCH CODE          : " AT LINE 12 COL 1.
             DISPLAY EBRNID AT LINE 12 COL 25.
             DISPLAY " DESIGNATION CODE     : " AT LINE 13 COL 1.
             DISPLAY EDESID AT LINE 13 COL 25.
             CLOSE EMPFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         LEAVE-PARA.
             DISPLAY ERASE-1
             OPEN INPUT LEAVEFILE.
             DISPLAY "ENTER CODE :" AT LINE 23 COL 25.
             ACCEPT LEMPID AT LINE 23 COL 38.
             DISPLAY ERASE-1
             READ LEAVEFILE INVALID KEY GO TO ERROR-LEAVE-PARA.
             DISPLAY " CODE           : " AT LINE 1 COL 1.
             DISPLAY LEMPID AT LINE 1 COL 19.
             DISPLAY " DATE           : " AT LINE 2 COL 1.
             DISPLAY LFMDATE AT LINE 2 COL 19.
             DISPLAY " DATE           : " AT LINE 3 COL 1.
             DISPLAY LTODATE AT LINE 3 COL 19.
             DISPLAY " LEAVE CATEGORY : " AT LINE 4 COL 1.
             DISPLAY LLEVCAT AT LINE 4 COL 19.
             CLOSE LEAVEFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         LIST-BRANCH-PARA.
             DISPLAY ERASE-1
             OPEN INPUT BRANCHFILE.
             DISPLAY " CITY : " AT LINE 1 COL 1.
             ACCEPT LBCITY AT LINE 1 COL 8.
             OPEN INPUT BRANCHFILE.
             MOVE ' ' TO BBRID.
             MOVE 1 TO LBLINE.
             DISPLAY ERASE-1
             PERFORM UNTIL FSB = 10
                IF LBCITY = ' '
                    DISPLAY " BRANCH CODE:    "
                        AT LINE LBLINE COL 1
                    DISPLAY BBRID
                        AT LINE LBLINE COL 17
                    ADD 1 TO LBLINE
                    DISPLAY " BRANCH NAME:    "
                        AT LINE LBLINE COL 1
                    DISPLAY BBRNAME
                        AT LINE LBLINE COL 17
                    ADD 1 TO LBLINE
                    DISPLAY " BRANCH ADDRESS: "
                        AT LINE LBLINE COL 1
                    DISPLAY BBRADD
                        AT LINE LBLINE COL 17
                    ADD 1 TO LBLINE
                    DISPLAY " PHONE:          "
                        AT LINE LBLINE COL 1
                    DISPLAY BBRPH
                        AT LINE LBLINE COL 17
                    ADD 1 TO LBLINE
                    DISPLAY " E-MAIL:         "
                        AT LINE LBLINE COL 1
                    DISPLAY BEMAIL
                        AT LINE LBLINE COL 17
                    ADD 1 TO LBLINE
                    DISPLAY " MANAGER NAME:   "
                        AT LINE LBLINE COL 1
                    DISPLAY BBRNAME
                        AT LINE LBLINE COL 17
                    ADD 2 TO LBLINE
                END-IF
                READ BRANCHFILE NEXT RECORD
                    AT END GO TO LIST-BRANCH-EXIT-PARA
                END-READ
                IF LBLINE = 22
                    DISPLAY "PRESS ENTER TO CONTINUE"
                        AT LINE 21 COL 10
                    ACCEPT OMIT AT LINE 21 COL 33
                    DISPLAY ERASE-1
                    MOVE 1 TO LBLINE
                END-IF
             END-PERFORM.
         LIST-BRANCH-EXIT-PARA.
             CLOSE BRANCHFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU"
                AT LINE 21 COL 10.
             ACCEPT OMIT AT LINE 21 COL 50.
             GO TO MAIN-PARA.

         BRANCH-PARA.
             DISPLAY ERASE-1
             OPEN INPUT BRANCHFILE.
             DISPLAY " BRANCH CODE :" AT LINE 23 COL 25.
             ACCEPT BBRID AT LINE 23 COL 40.
             DISPLAY ERASE-1
             READ BRANCHFILE INVALID KEY GO TO ERROR-BRANCH-PARA.
             DISPLAY " BRANCH CODE    : " AT LINE 1 COL 1.
             DISPLAY BBRID AT LINE 1 COL 19.
             DISPLAY " BRANCH NAME    : " AT LINE 2 COL 1.
             DISPLAY BBRNAME AT LINE 2 COL 19.
             DISPLAY " BRANCH ADDRESS : " AT LINE 3 COL 1.
             DISPLAY BBRADD AT LINE 3 COL 19.
             DISPLAY " PHONE          : " AT LINE 4 COL 1.
             DISPLAY BBRPH AT LINE 4 COL 19.
             DISPLAY " E-MAIL         : " AT LINE 5 COL 1.
             DISPLAY BEMAIL AT LINE 5 COL 19.
             DISPLAY " MANAGER NAME   : " AT LINE 6 COL 1.
             DISPLAY BMGRNAME AT LINE 6 COL 19.
             CLOSE BRANCHFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         DESIGNATION-PARA.
             DISPLAY ERASE-1
             OPEN INPUT DESIGNATIONFILE.
             DISPLAY "ENTER THE DESIGNATION CODE :" AT LINE 23 COL 25.
             ACCEPT DES AT LINE 23 COL 54.
             DISPLAY ERASE-1
             PERFORM DES-READ-PARA UNTIL FSDES = 10.
         DES-READ-PARA.
             READ DESIGNATIONFILE AT END GO TO DES-EXIT-PARA.
             IF DESID = DES
	             DISPLAY " DESIGNATION CODE     : " AT LINE 1 COL 1
	             DISPLAY DESID AT LINE 1 COL 25
	             DISPLAY " DESIGNATION          : " AT LINE 2 COL 1
	             DISPLAY DESIGN AT LINE 2 COL 25
	             DISPLAY " DESIGNATION IN SHORT : " AT LINE 3 COL 1
	             DISPLAY DESHRT AT LINE 3 COL 25.
         DES-EXIT-PARA.
             CLOSE DESIGNATIONFILE.
             DISPLAY ' '.
             DISPLAY ' '.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU"
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         DEPARTMENT-PARA.
             DISPLAY ERASE-1
             OPEN INPUT DEPARTMENTFILE.
             DISPLAY "ENTER DEP CODE :" AT LINE 23 COL 25.
             ACCEPT DEPCODE AT LINE 23 COL 42.
             DISPLAY ERASE-1
             READ DEPARTMENTFILE INVALID KEY
                GO TO ERROR-DEPARTMENT-PARA.
             DISPLAY " DEPARTMENT CODE : " AT LINE 1 COL 1.
             DISPLAY DEPCODE AT LINE 1 COL 20.
             DISPLAY " DEPARTMENT NAME : " AT LINE 2 COL 1.
             DISPLAY DEPNAME AT LINE 2 COL 20.
             CLOSE DEPARTMENTFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         REVISION-PARA.
             DISPLAY ERASE-1
             OPEN INPUT REVISIONFILE.
             DISPLAY "ENTER REVISION CODE: " AT LINE 23 COL 25.
             ACCEPT RREVID AT LINE 23 COL 46.
             DISPLAY ERASE-1
             READ REVISIONFILE INVALID KEY
                      GO TO ERROR-REVISION-PARA.
             DISPLAY " REVISION CODE           : " AT LINE 1 COL 1.
             DISPLAY RREVID AT LINE 1 COL 28.
             DISPLAY " EMPLOYEE CODE           : " AT LINE 2 COL 1.
             DISPLAY REMPID AT LINE 2 COL 28.
             DISPLAY " DESIGNATION CODE        : " AT LINE 3 COL 1.
             DISPLAY RDESCODE AT LINE 3 COL 28.
             DISPLAY " BASIC                   : " AT LINE 4 COL 1.
             DISPLAY RBASIC AT LINE 4 COL 28.
             DISPLAY " HRA                     : " AT LINE 5 COL 1.
             DISPLAY RHRA AT LINE 5 COL 28.
             DISPLAY " DPA                     : " AT LINE 6 COL 1.
             DISPLAY RDPA AT LINE 6 COL 28.
             DISPLAY " PPA                     : " AT LINE 7 COL 1.
             DISPLAY RPPA AT LINE 7 COL 28.
             DISPLAY " EDUCATIONAL ALLOWANCE   : " AT LINE 8 COL 1.
             DISPLAY REDUA AT LINE 8 COL 28.
             DISPLAY " TECHNICAL JOURNAL       : " AT LINE 9 COL 1.
             DISPLAY RTECHJR AT LINE 9 COL 28.
             DISPLAY " LUNCH ALLOWANCE         : " AT LINE 10 COL 1.
             DISPLAY RLUNCHA AT LINE 10 COL 28.
             DISPLAY " CONVEYANCE              : " AT LINE 11 COL 1.
             DISPLAY RCONVEY AT LINE 11 COL 28.
             DISPLAY " BUSINESS ATTIREMENT     : " AT LINE 12 COL 1.
             DISPLAY RBUSATR AT LINE 12 COL 28.
             DISPLAY " LEAVE TRAVEL ALLOWANCE  : " AT LINE 13 COL 1.
             DISPLAY RLTA AT LINE 13 COL 28.
             DISPLAY " PF                      : " AT LINE 14 COL 1.
             DISPLAY RPF AT LINE 14 COL 28.
             DISPLAY " ESI                     : " AT LINE 15 COL 1.
             DISPLAY RESI AT LINE 15 COL 28.
             DISPLAY " REVISED DATE            : " AT LINE 16 COL 1.
             DISPLAY RREVDATE AT LINE 16 COL 28.
             CLOSE REVISIONFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         PAYMENT-PARA.
             DISPLAY ERASE-1
             OPEN INPUT PAYMENTFILE.
             DISPLAY "ENTER EMP CODE :" AT LINE 23 COL 25.
             ACCEPT PEMPID AT LINE 23 COL 42.
             DISPLAY ERASE-1
             READ PAYMENTFILE INVALID KEY GO TO ERROR-PAYMENT-PARA.
             DISPLAY " EMPLOYEE CODE                  :"
                AT LINE 1 COL 1.
             DISPLAY PEMPID AT LINE 1 COL 35.
             DISPLAY " BASIC                          :" 
               AT LINE 2 COL 1.
             DISPLAY PBASIC AT LINE 2 COL 35.
             DISPLAY " DEARNESS ALLOWANCE             :" 
               AT LINE 3 COL 1.
             DISPLAY PDA AT LINE 3 COL 35.
             DISPLAY " CITY COMPENSATORY ALLOWANCE    :" 
               AT LINE 4 COL 1.
             DISPLAY PCCA AT LINE 4 COL 35.
             DISPLAY " HRA                            :" 
               AT LINE 5 COL 1.
             DISPLAY PHRA AT LINE 5 COL 35.
             DISPLAY " DPA                            :" 
               AT LINE 6 COL 1.
             DISPLAY PDPA AT LINE 6 COL 35.
             DISPLAY " PPA                            :" 
               AT LINE 7 COL 1.
             DISPLAY PPPA AT LINE 7 COL 35.
             DISPLAY " EDUCATIONAL ALLOWANCE          :" 
               AT LINE 8 COL 1.
             DISPLAY PEDUA AT LINE 8 COL 35.
             DISPLAY " TECHNICAL JOURNAL              :" 
               AT LINE 9 COL 1.
             DISPLAY PTECHJR AT LINE 9 COL 35.
             DISPLAY " LUNCH ALLOWANCE                :" 
               AT LINE 10 COL 1.
             DISPLAY PLUNCHA AT LINE 10 COL 35.
             DISPLAY " CONVEYANCE                     :" 
               AT LINE 11 COL 1.
             DISPLAY PCONVEY AT LINE 11 COL 35.
             DISPLAY " BUSINESS ATTIREMENT            :" 
               AT LINE 12 COL 1.
             DISPLAY PBUSATR AT LINE 12 COL 35.
             DISPLAY " LEAVE TRAVEL ALLOWANCE         :" 
               AT LINE 13 COL 1.
             DISPLAY PLTA AT LINE 13 COL 35.
             DISPLAY " PF                             :" 
               AT LINE 14 COL 1.
             DISPLAY PPF AT LINE 14 COL 35.
             DISPLAY " ESI                            :" 
               AT LINE 15 COL 1.
             DISPLAY PESI AT LINE 15 COL 35.
             DISPLAY " GRATUITY                       :" 
               AT LINE 16 COL 1.
             DISPLAY PGRTY AT LINE 16 COL 35.
             DISPLAY " PROFESSIONAL TAX               :" 
               AT LINE 17 COL 1.
             DISPLAY PPTAX AT LINE 17 COL 35.
             DISPLAY " INCOME TAX                     :" 
               AT LINE 18 COL 1.
             DISPLAY PITAX AT LINE 18 COL 35.
             DISPLAY " LOAN                           :" 
               AT LINE 19 COL 1.
             DISPLAY PLOAN AT LINE 19 COL 35.
             DISPLAY " LOAN DEDUCTION AMOUNT          :" 
               AT LINE 20 COL 1.
             DISPLAY PLOANDA AT LINE 20 COL 35.
             DISPLAY " OTHER DEDUCTION                :" 
               AT LINE 21 COL 1.
             DISPLAY POTHERD AT LINE 21 COL 35.
             DISPLAY " PERFORMANCE INCENTIVE          :" 
               AT LINE 22 COL 1.
             DISPLAY PPERINC AT LINE 22 COL 35.
             DISPLAY " MEDICAL REIMBURSEMENT          :" 
               AT LINE 23 COL 1.
             DISPLAY PMEDI AT LINE 23 COL 35.
             DISPLAY " BOOK REIMBURSEMENT             :" 
               AT LINE 24 COL 1.
             DISPLAY PBOOK AT LINE 24 COL 35.
             DISPLAY "PRESS ENTER TO CONTINUE"
                AT LINE 25 COL 10.
             ACCEPT OMIT AT LINE 25 COL 24.
             DISPLAY ERASE-1
             DISPLAY " ENTERTAINMENT                  :"
               AT LINE 1 COL 1.
             DISPLAY PENTER AT LINE 1 COL 35.
             DISPLAY " PHONE                          :" 
               AT LINE 2 COL 1.
             DISPLAY PTPH AT LINE 2 COL 35.
             DISPLAY " HOUSE RELATED                  :" 
               AT LINE 3 COL 1.
             DISPLAY PHOUSE AT LINE 3 COL 35.
             DISPLAY " VEHICLE MAINTENANCE            :" 
               AT LINE 4 COL 1.
             DISPLAY PVEHMAN AT LINE 4 COL 35.
             DISPLAY " CREDIT CARD                    :" 
               AT LINE 5 COL 1.
             DISPLAY PCREDIT AT LINE 5 COL 35.
             DISPLAY " CLUB                           :" 
               AT LINE 6 COL 1.
             DISPLAY PCLUB AT LINE 6 COL 35.
             DISPLAY " CASUAL LEAVE                   :" 
               AT LINE 7 COL 1.
             DISPLAY PCL AT LINE 7 COL 35.
             DISPLAY " SICK LEAVE                     :" 
               AT LINE 8 COL 1.
             DISPLAY PSL AT LINE 8 COL 35.
             DISPLAY " PAID LEAVE                     :" 
               AT LINE 9 COL 1.
             DISPLAY PPL AT LINE 9 COL 35.
             DISPLAY " LEAVE LOSS OF PAY              :" 
               AT LINE 10 COL 1.
             DISPLAY PLLOP AT LINE 10 COL 35.
             DISPLAY " OTHER LEAVES                   :" 
               AT LINE 11 COL 1.
             DISPLAY POTHERL AT LINE 11 COL 35.
             CLOSE PAYMENTFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         CONFIRMATION-PARA.
             DISPLAY ERASE-1
             OPEN INPUT CONFIRMATIONFILE.
             DISPLAY "ENTER CODE :" AT LINE 23 COL 25.
             ACCEPT CCONID AT LINE 23 COL 38.
             DISPLAY ERASE-1
             READ CONFIRMATIONFILE INVALID KEY
             	GO TO ERROR-CONFIRMATION-PARA.
             DISPLAY " CONFIRMATION CODE :" AT LINE 1 COL 1.
             DISPLAY CCONID AT LINE 1 COL 22.
             DISPLAY " EMPLOYEE CODE     :" AT LINE 2 COL 1.
             DISPLAY CEMPID AT LINE 2 COL 22.
             DISPLAY " CONFIRMATION DATE :" AT LINE 3 COL 1.
             DISPLAY CCDATE AT LINE 3 COL 22.
             CLOSE CONFIRMATIONFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         GRADE-PARA.
             DISPLAY ERASE-1
             OPEN INPUT GRADEFILE.
             DISPLAY "ENTER GRADE NO. :" AT LINE 23 COL 25.
             ACCEPT GR AT LINE 23 COL 43.
             DISPLAY ERASE-1
             PERFORM GR-READ-PARA UNTIL FSG = 10.
         GR-READ-PARA.
             READ GRADEFILE AT END GO TO GR-EXIT-PARA.
             IF GGRADE = GR
	             DISPLAY " GRADE NO.   : " AT LINE 1 COL 1
	             DISPLAY GGRADE AT LINE 1 COL 16
	             DISPLAY " DESIGNATION : " AT LINE 2 COL 1
	             DISPLAY GDESIGN AT LINE 2 COL 16.
         GR-EXIT-PARA.
             CLOSE GRADEFILE.
             DISPLAY ' '.
             DISPLAY ' '.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU"
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         TRANSFER-PARA.
             DISPLAY ERASE-1
             OPEN INPUT TRANSFERFILE.
             DISPLAY "ENTER TRANSFER CODE : " AT LINE 23 COL 25.
             ACCEPT TTRFID AT LINE 23 COL 47.
             DISPLAY ERASE-1
             READ TRANSFERFILE INVALID KEY GO TO ERROR-TRANSFER-PARA.
             DISPLAY " TRANSFER CODE     : " AT LINE 1 COL 1.
             DISPLAY TTRFID AT LINE 1 COL 22.
             DISPLAY " EMP CODE          : " AT LINE 2 COL 1.
             DISPLAY TEMPID AT LINE 2 COL 22.
             DISPLAY " OLD BRANCH CODE   : " AT LINE 3 COL 1.
             DISPLAY TOBRID AT LINE 3 COL 22.
             DISPLAY " TRANSFER DATE     : " AT LINE 4 COL 1.
             DISPLAY TTRFDT AT LINE 4 COL 22.
             CLOSE TRANSFERFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         EMPPERSONAL-PARA.
             DISPLAY ERASE-1
             OPEN INPUT EMPPERSONALFILE.
             DISPLAY "ENTER EMP CODE :" AT LINE 23 COL 25.
             ACCEPT EPEMPID AT LINE 23 COL 42.
             DISPLAY ERASE-1
             READ EMPPERSONALFILE INVALID KEY
                             GO TO ERROR-EMPPERSONAL-PARA.
             DISPLAY " EMPLOYEE CODE     :" AT LINE 1 COL 1.
             DISPLAY EPEMPID AT LINE 1 COL 21.
             DISPLAY " TEMPORARY ADDRESS :" AT LINE 2 COL 1.
             DISPLAY EPTADD AT LINE 2 COL 21.
             DISPLAY " PHONE             :" AT LINE 3 COL 1.
             DISPLAY EPTPH AT LINE 3 COL 21.
             DISPLAY " DOB               :" AT LINE 4 COL 1.
             DISPLAY EPDOB AT LINE 4 COL 21.
             DISPLAY " POB               :" AT LINE 5 COL 1.
             DISPLAY EPPOB AT LINE 5 COL 21.
             DISPLAY " LANGUAGE KNOWN    :" AT LINE 6 COL 1.
             DISPLAY EPLANG AT LINE 6 COL 21.
             DISPLAY " BLOOD GROUP       :" AT LINE 7 COL 1.
             DISPLAY EPBLOOD AT LINE 7 COL 21.
             DISPLAY " WEIGHT            :" AT LINE 8 COL 1.
             DISPLAY EPWEIGHT AT LINE 8 COL 21.
             DISPLAY " HEIGHT            :" AT LINE 9 COL 1.
             DISPLAY EPHEIGHT AT LINE 9 COL 21.
             DISPLAY " VISION           :" AT LINE 10 COL 1.
             DISPLAY EPVISION AT LINE 10 COL 21.
             DISPLAY " FATHER'S NAME    :" AT LINE 11 COL 1.
             DISPLAY EPFATHER AT LINE 11 COL 21.
             DISPLAY " DOB OF FATHER    :" AT LINE 12 COL 1.
             DISPLAY EPDOBF AT LINE 12 COL 21.
             DISPLAY " MOTHER'S NAME    :" AT LINE 13 COL 1.
             DISPLAY EPMOTHER AT LINE 13 COL 21.
             DISPLAY " DOB OF MOTHER    :" AT LINE 14 COL 1.
             DISPLAY EPDOBM AT LINE 14 COL 21.
             DISPLAY " SPOUSE NAME      :" AT LINE 15 COL 1.
             DISPLAY EPSPOUSE AT LINE 15 COL 21.
             DISPLAY " CHILD NAME       :" AT LINE 16 COL 1.
             DISPLAY EPCHILD AT LINE 16 COL 21.
             DISPLAY " DOB OF CHILD     :" AT LINE 17 COL 1.
             DISPLAY EPDOBC AT LINE 17 COL 21.
             CLOSE EMPPERSONALFILE.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU"
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-EMP-PARA.
             CLOSE EMPFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-LEAVE-PARA.
             CLOSE LEAVEFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-BRANCH-PARA.
             CLOSE BRANCHFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-DEPARTMENT-PARA.
             CLOSE DEPARTMENTFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-REVISION-PARA.
             CLOSE REVISIONFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-PAYMENT-PARA.
             CLOSE PAYMENTFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-CONFIRMATION-PARA.
             CLOSE CONFIRMATIONFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-TRANSFER-PARA.
             CLOSE TRANSFERFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.

         ERROR-EMPPERSONAL-PARA.
             CLOSE EMPPERSONALFILE.
             DISPLAY ERASE-1
             DISPLAY "INVALID CODE" AT LINE 12 COL 30.
             DISPLAY "PRESS ENTER TO RETURN TO HRMS READ MENU" 
                AT LINE 20 COL 10.
             ACCEPT OMIT AT LINE 21 COL 10.
             GO TO MAIN-PARA.
       END PROGRAM EMPREAD.

       IDENTIFICATION DIVISION.
       PROGRAM-ID. EMPWRITE.

         ENVIRONMENT DIVISION.
         INPUT-OUTPUT SECTION.
         FILE-CONTROL.
             SELECT EMPFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS EEMPID
                 FILE STATUS IS FSO.

             SELECT LEAVEFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS LEMPID
                 FILE STATUS IS FSL.

             SELECT BRANCHFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS BBRID
                 FILE STATUS IS FSB.

             SELECT DESIGNATIONFILE ASSIGN TO DISK
                 ORGANIZATION IS SEQUENTIAL
                 ACCESS MODE IS SEQUENTIAL
                 FILE STATUS IS FSDES.

             SELECT DEPARTMENTFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS DEPCODE
                 FILE STATUS IS FSDEP.

             SELECT REVISIONFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS RREVID
                 ALTERNATE RECORD KEY IS REMPID
                 FILE STATUS IS FSR.

             SELECT PAYMENTFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS PEMPID
                 FILE STATUS IS FSP.

             SELECT CONFIRMATIONFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS CCONID
                 ALTERNATE RECORD KEY IS CEMPID
                 FILE STATUS IS FSC.

             SELECT GRADEFILE ASSIGN TO DISK
                 ORGANIZATION IS SEQUENTIAL
                 ACCESS MODE IS SEQUENTIAL
                 FILE STATUS IS FSG.

             SELECT TRANSFERFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS TTRFID
                 FILE STATUS IS FST.

             SELECT EMPPERSONALFILE ASSIGN TO DISK
                 ORGANIZATION IS INDEXED
                 ACCESS MODE IS DYNAMIC
                 RECORD KEY IS EPEMPID
                 FILE STATUS IS FSEP.

         DATA DIVISION.
         FILE SECTION.
         FD EMPFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "EMP.DAT".
         01 EMPREC.
             02 EEMPID    PIC X(6).
             02 EEMPNAME  PIC X(25).
             02 EEMPADDR  PIC X(30).
             02 EPHONE    PIC X(10).
             02 EDOJ      PIC X(10).
             02 EDIP      PIC X(10).
             02 EUG       PIC X(4).
             02 EPG       PIC X(4).
             02 EPROFQ    PIC X(4).
             02 ESKILL    PIC X(10).
             02 EGRDNO    PIC 99.
             02 EBRNID    PIC X(6).
             02 EDESID    PIC X(6).

         FD LEAVEFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "LEAVE.DAT".
         01 LEAVEREC.
             02 LEMPID    PIC X(6).
             02 LFMDATE   PIC X(10).
             02 LTODATE   PIC X(10).
             02 LLEVCAT   PIC X(3).

         FD BRANCHFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "BRANCH.DAT".
         01 BRANCHREC.
             02 BBRID    PIC X(6).
             02 BBRNAME  PIC X(15).
             02 BBRADD   PIC X(30).
             02 BBRPH    PIC X(10).
             02 BEMAIL   PIC X(20).
             02 BMGRNAME PIC X(25).

         FD DESIGNATIONFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "DESIG.DAT".
         01 DESIGNATIONREC.
             02 DESID    PIC X(6).
             02 DESIGN   PIC X(15).
             02 DESHRT   PIC X(4).

         FD DEPARTMENTFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "DEPART.DAT".
         01 DEPARTMENTREC.
             02 DEPCODE  PIC X(6).
             02 DEPNAME  PIC X(20).

         FD REVISIONFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "REVISION.DAT".
         01 REVISIONREC.
             02 RREVID   PIC X(6).
             02 REMPID   PIC X(6).
             02 RDESCODE PIC X(6).
             02 RBASIC   PIC 9(6)V99.
             02 RHRA     PIC 9(6)V99.
             02 RDPA     PIC 9(6)V99.
             02 RPPA     PIC 9(6)V99.
             02 REDUA    PIC 9(6)V99.
             02 RTECHJR  PIC 9(6)V99.
             02 RLUNCHA  PIC 9(6)V99.
             02 RCONVEY  PIC 9(6)V99.
             02 RBUSATR  PIC 9(6)V99.
             02 RLTA     PIC 9(6)V99.
             02 RPF      PIC 9(6)V99.
             02 RESI     PIC 9(6)V99.
             02 RREVDATE PIC X(10).

         FD PAYMENTFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "PAYMENT.DAT".
         01 PAYMENTREC.
             02 PEMPID   PIC X(6).
             02 PBASIC   PIC 9(6)V99.
             02 PDA      PIC 9(6)V99.
             02 PCCA     PIC 9(6)V99.
             02 PHRA     PIC 9(6)V99.
             02 PDPA     PIC 9(6)V99.
             02 PPPA     PIC 9(6)V99.
             02 PEDUA    PIC 9(6)V99.
             02 PTECHJR  PIC 9(6)V99.
             02 PLUNCHA  PIC 9(6)V99.
             02 PCONVEY  PIC 9(6)V99.
             02 PBUSATR  PIC 9(6)V99.
             02 PLTA     PIC 9(6)V99.
             02 PPF      PIC 9(6)V99.
             02 PESI     PIC 9(6)V99.
             02 PGRTY    PIC 9(6)V99.
             02 PPTAX    PIC 9(6)V99.
             02 PITAX    PIC 9(6)V99.
             02 PLOAN    PIC 9(8)V99.
             02 PLOANDA  PIC 9(8)V99.
             02 POTHERD  PIC 9(6)V99.
             02 PPERINC  PIC 9(6)V99.
             02 PMEDI    PIC 9(6)V99.
             02 PBOOK    PIC 9(6)V99.
             02 PENTER   PIC 9(6)V99.
             02 PTPH     PIC 9(6)V99.
             02 PHOUSE   PIC 9(6)V99.
             02 PVEHMAN  PIC 9(6)V99.
             02 PCREDIT  PIC 9(6)V99.
             02 PCLUB    PIC 9(6)V99.
             02 PCL      PIC 99.
             02 PSL      PIC 99.
             02 PPL      PIC 99.
             02 PLLOP    PIC 999.
             02 POTHERL  PIC 999.

         FD CONFIRMATIONFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "CONFIRM.DAT".
         01 CONFIRMATIONREC.
             02 CCONID   PIC X(6).
             02 CEMPID   PIC X(6).
             02 CCDATE   PIC X(6).

         FD GRADEFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "GRADE.DAT".
         01 GRADEREC.
             02 GGRADE   PIC 99.
             02 GDESIGN  PIC X(25).

         FD TRANSFERFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "TRANSFER.DAT".
         01 TRANSFERREC.
             02 TTRFID   PIC X(6).
             02 TEMPID   PIC X(6).
             02 TOBRID   PIC X(6).
             02 TTRFDT   PIC X(10).

         FD EMPPERSONALFILE
             LABEL RECORDS ARE STANDARD
             VALUE OF FILE-ID IS "EMPPER.DAT".
         01 EMPPERSONALREC.
             02 EPEMPID  PIC X(6).
             02 EPTADD   PIC X(30).
             02 EPTPH    PIC X(10).
             02 EPDOB    PIC X(10).
             02 EPPOB    PIC X(10).
             02 EPLANG   PIC X(15).
             02 EPBLOOD  PIC X(4).
             02 EPWEIGHT PIC 999.
             02 EPHEIGHT PIC 999.
             02 EPVISION PIC X(15).
             02 EPFATHER PIC X(25).
             02 EPDOBF   PIC X(10).
             02 EPMOTHER PIC X(25).
             02 EPDOBM   PIC X(10).
             02 EPSPOUSE PIC X(25).
             02 EPCHILD  PIC X(25).
             02 EPDOBC   PIC X(10).

         WORKING-STORAGE SECTION.
         77 FSO   PIC XX.
         77 FSL   PIC XX.
         77 FSB   PIC XX.
         77 FSDES PIC XX.
         77 FSDEP PIC XX.
         77 FSR   PIC XX.
         77 FSP   PIC XX.
         77 FSC   PIC XX.
         77 FSG   PIC XX.
         77 FST   PIC XX.
         77 FSEP  PIC XX.
         77 CHOICE PIC 99.

         SCREEN SECTION.
         01 ERASE-1.
            02 BLANK SCREEN.

         PROCEDURE DIVISION.
         MAIN-PARA.
             DISPLAY ERASE-1
             DISPLAY "*******************************************"
               AT LINE 3 COL 10.
             DISPLAY "     HUMAN RESOURCE MANAGEMENT SYSTEM      "
               AT LINE 5 COL 10.
             DISPLAY "*******************************************"
               AT LINE 7 COL 10.
             DISPLAY "01. EMPLOYEE FILE" AT LINE 11 COL 5.
             DISPLAY "02. LEAVE FILE" AT LINE 12 COL 5.
             DISPLAY "03. BRANCH FILE" AT LINE 13 COL 5.
             DISPLAY "04. DESIGNATION FILE" AT LINE 14 COL 5.
             DISPLAY "05. DEPARTMENT FILE" AT LINE 15 COL 5.
             DISPLAY "06. REVISION FILE" AT LINE 16 COL 5.
             DISPLAY "07. PAYMENT FILE" AT LINE 17 COL 5.
             DISPLAY "08. CONFIRMATION FILE" AT LINE 18 COL 5.
             DISPLAY "09. GRADE FILE" AT LINE 19 COL 5.
             DISPLAY "10. TRANSFER FILE" AT LINE 20 COL 5.
             DISPLAY "11. EMPLOYEE PERSONAL FILE" AT LINE 21 COL 5.
             DISPLAY "12. EXIT" AT LINE 22 COL 5.
             DISPLAY "ENTER U R CHOICE :" AT LINE 23 COL 25.
             ACCEPT CHOICE AT LINE 23 COL 45.
             EVALUATE CHOICE
                WHEN 1     GO TO EMP-PARA
                WHEN 2     GO TO LEAVE-PARA
                WHEN 3     GO TO BRANCH-PARA
                WHEN 4     GO TO DESIGNATION-PARA
                WHEN 5     GO TO DEPARTMENT-PARA
                WHEN 6     GO TO REVISION-PARA
                WHEN 7     GO TO PAYMENT-PARA
                WHEN 8     GO TO CONFIRMATION-PARA
                WHEN 9     GO TO GRADE-PARA
                WHEN 10    GO TO TRANSFER-PARA
                WHEN 11    GO TO EMPPERSONAL-PARA
                WHEN OTHER EXIT PROGRAM.

         EMP-PARA.
             DISPLAY ERASE-1
             OPEN I-O EMPFILE.
             IF FSO <> 00
                OPEN OUTPUT EMPFILE.
             DISPLAY "ENTER CODE :" AT LINE 1 COL 1.
             ACCEPT EEMPID AT LINE 1 COL 35.
             DISPLAY "ENTER NAME :" AT LINE 2 COL 1.
             ACCEPT EEMPNAME AT LINE 2 COL 35.
             DISPLAY "ENTER ADDRESS :" AT LINE 3 COL 1.
             ACCEPT EEMPADDR AT LINE 3 COL 35.
             DISPLAY "ENTER PHONE :" AT LINE 4 COL 1.
             ACCEPT EPHONE AT LINE 4 COL 35.
             DISPLAY "ENTER DATE OF JOIN :" AT LINE 5 COL 1.
             ACCEPT EDOJ AT LINE 5 COL 35.
             DISPLAY "ENTER DIPLOMA :" AT LINE 6 COL 1.
             ACCEPT EDIP AT LINE 6 COL 35.
             DISPLAY "ENTER UG :" AT LINE 7 COL 1.
             ACCEPT EUG AT LINE 7 COL 35.
             DISPLAY "ENTER PG :" AT LINE 8 COL 1.
             ACCEPT EPG AT LINE 8 COL 35.
             DISPLAY "ENTER PROFESSIONAL QUALITY :" AT LINE 9 COL 1.
             ACCEPT EPROFQ AT LINE 9 COL 35.
             DISPLAY "ENTER SKILL SET :" AT LINE 10 COL 1.
             ACCEPT ESKILL AT LINE 10 COL 35.
             DISPLAY "ENTER GRADE NUMBER :" AT LINE 11 COL 1.
             ACCEPT EGRDNO AT LINE 11 COL 35.
             DISPLAY "ENTER BRANCH CODE :" AT LINE 12 COL 1.
             ACCEPT EBRNID AT LINE 12 COL 35.
             DISPLAY "ENTER DESIGNATION CODE :" AT LINE 13 COL 1.
             ACCEPT EDESID AT LINE 13 COL 35.
             WRITE EMPREC.
             CLOSE EMPFILE.
             GO TO MAIN-PARA.

         LEAVE-PARA.
             DISPLAY ERASE-1
             OPEN I-O LEAVEFILE.
             *>IF FSL = 30
             IF FSL <> 00
                OPEN OUTPUT LEAVEFILE.
             DISPLAY "ENTER CODE :" AT LINE 1 COL 1.
             ACCEPT LEMPID AT LINE 1 COL 35.
             DISPLAY "ENTER FROM DATE :" AT LINE 2 COL 1.
             ACCEPT LFMDATE AT LINE 2 COL 35.
             DISPLAY "ENTER TO DATE :" AT LINE 3 COL 1.
             ACCEPT LTODATE AT LINE 3 COL 35.
             DISPLAY "ENTER LEAVE CATEGORY :" AT LINE 4 COL 1.
             ACCEPT LLEVCAT AT LINE 4 COL 35.
             WRITE LEAVEREC.
             CLOSE LEAVEFILE.
             GO TO MAIN-PARA.

         BRANCH-PARA.
             DISPLAY ERASE-1
             OPEN I-O BRANCHFILE.
             *>IF FSL = 30
             IF FSL <> 00
                OPEN OUTPUT BRANCHFILE.
             DISPLAY "ENTER BRANCH CODE :" AT LINE 1 COL 1.
             ACCEPT BBRID AT LINE 1 COL 35.
             DISPLAY "ENTER BRANCH NAME :" AT LINE 2 COL 1.
             ACCEPT BBRNAME AT LINE 2 COL 35.
             DISPLAY "ENTER BRANCH ADDRESS :" AT LINE 3 COL 1.
             ACCEPT BBRADD AT LINE 3 COL 35.
             DISPLAY "ENTER PHONE :" AT LINE 4 COL 1.
             ACCEPT BBRPH AT LINE 4 COL 35.
             DISPLAY "ENTER E-MAIL :" AT LINE 5 COL 1.
             ACCEPT BEMAIL AT LINE 5 COL 35.
             DISPLAY "ENTER MANAGER NAME :" AT LINE 6 COL 1.
             ACCEPT BMGRNAME AT LINE 6 COL 35.
             WRITE BRANCHREC.
             CLOSE BRANCHFILE.
             GO TO MAIN-PARA.

         DESIGNATION-PARA.
             DISPLAY ERASE-1
             OPEN EXTEND DESIGNATIONFILE.
             IF FSDES <> 00
             	OPEN OUTPUT DESIGNATIONFILE.
             DISPLAY "ENTER DESIGNATION CODE :" AT LINE 1 COL 1.
             ACCEPT DESID AT LINE 1 COL 35.
             DISPLAY "ENTER DESIGNATION :" AT LINE 2 COL 1.
             ACCEPT DESIGN AT LINE 2 COL 35.
             DISPLAY "ENTER DES IN SHORT :" AT LINE 3 COL 1.
             ACCEPT DESHRT AT LINE 3 COL 35.
             WRITE DESIGNATIONREC.
             CLOSE DESIGNATIONFILE.
             GO TO MAIN-PARA.

         DEPARTMENT-PARA.
             DISPLAY ERASE-1
             OPEN I-O DEPARTMENTFILE.
             *>IF FSDEP = 30
             IF FSDEP <> 00
                OPEN OUTPUT DEPARTMENTFILE.
             DISPLAY "ENTER DEPARTMENT CODE :" AT LINE 1 COL 1.
             ACCEPT DEPCODE AT LINE 1 COL 35.
             DISPLAY "ENTER DEPARTMENT NAME :" AT LINE 2 COL 1.
             ACCEPT DEPNAME AT LINE 2 COL 35.
             WRITE DEPARTMENTREC.
             CLOSE DEPARTMENTFILE.
             GO TO MAIN-PARA.

         REVISION-PARA.
             DISPLAY ERASE-1
             OPEN I-O REVISIONFILE.
             *>IF FSR = 30
             IF FSR <> 00
                OPEN OUTPUT REVISIONFILE.
             DISPLAY "ENTER REVISION CODE :" AT LINE 1 COL 1.
             ACCEPT RREVID AT LINE 1 COL 35.
             DISPLAY "ENTER EMPLOYEE CODE :" AT LINE 2 COL 1.
             ACCEPT REMPID AT LINE 2 COL 35.
             DISPLAY "ENTER DESIGNATION CODE :" AT LINE 3 COL 1.
             ACCEPT RDESCODE AT LINE 3 COL 35.
             DISPLAY "ENTER BASIC :" AT LINE 4 COL 1.
             ACCEPT RBASIC AT LINE 4 COL 35.
             DISPLAY "ENTER HRA :" AT LINE 5 COL 1.
             ACCEPT RHRA AT LINE 5 COL 35.
             DISPLAY "ENTER DPA :" AT LINE 6 COL 1.
             ACCEPT RDPA AT LINE 6 COL 35.
             DISPLAY "ENTER PPA :" AT LINE 7 COL 1.
             ACCEPT RPPA AT LINE 7 COL 35.
             DISPLAY "ENTER EDUCATIONAL ALLOWANCE :" AT LINE 8 COL 1.
             ACCEPT REDUA AT LINE 8 COL 35.
             DISPLAY "ENTER TECH. JOURNAL :" AT LINE 9 COL 1.
             ACCEPT RTECHJR AT LINE 9 COL 35.
             DISPLAY "ENTER LUNCH ALLOWANCE :" AT LINE 10 COL 1.
             ACCEPT RLUNCHA AT LINE 10 COL 35.
             DISPLAY "ENTER CONVEYANCE :" AT LINE 11 COL 1.
             ACCEPT RCONVEY AT LINE 11 COL 35.
             DISPLAY "ENTER BUSINESS ATTIREMENT :" AT LINE 12 COL 1.
             ACCEPT RBUSATR AT LINE 12 COL 35.
             DISPLAY "ENTER LEAVE TRAVEL ALLOWANCE :" AT LINE 13 COL 1.
             ACCEPT RLTA AT LINE 13 COL 35.
             DISPLAY "ENTER PF :" AT LINE 14 COL 1.
             ACCEPT RPF AT LINE 14 COL 35.
             DISPLAY "ENTER ESI :" AT LINE 15 COL 1.
             ACCEPT RESI AT LINE 15 COL 35.
             DISPLAY "ENTER REVISED DATE :" AT LINE 16 COL 1.
             ACCEPT RREVDATE AT LINE 16 COL 35.
             WRITE REVISIONREC.
             CLOSE REVISIONFILE.
             GO TO MAIN-PARA.

         PAYMENT-PARA.
             DISPLAY ERASE-1
             OPEN I-O PAYMENTFILE.
             *>IF FSP = 30
             IF FSP <> 00
                OPEN OUTPUT PAYMENTFILE.
             DISPLAY "ENTER EMPLOYEE CODE :" AT LINE 1 COL 1.
             ACCEPT PEMPID AT LINE 1 COL 35.
             DISPLAY "ENTER BASIC :" AT LINE 2 COL 1.
             ACCEPT PBASIC AT LINE 2 COL 35.
             DISPLAY "ENTER DA :" AT LINE 3 COL 1.
             ACCEPT PDA AT LINE 3 COL 35.
             DISPLAY "ENTER CCA :" AT LINE 4 COL 1.
             ACCEPT PCCA AT LINE 4 COL 35.
             DISPLAY "ENTER HRA :" AT LINE 5 COL 1.
             ACCEPT PHRA AT LINE 5 COL 35.
             DISPLAY "ENTER DPA :" AT LINE 6 COL 1.
             ACCEPT PDPA AT LINE 6 COL 35.
             DISPLAY "ENTER PPA :" AT LINE 7 COL 1.
             ACCEPT PPPA AT LINE 7 COL 35.
             DISPLAY "ENTER EDUCATIONAL ALLOWANCE :" AT LINE 8 COL 1.
             ACCEPT PEDUA AT LINE 8 COL 35.
             DISPLAY "ENTER TECH. JOURNAL :" AT LINE 9 COL 1.
             ACCEPT PTECHJR AT LINE 9 COL 35.
             DISPLAY "ENTER LUNCH ALLOWANCE :" AT LINE 10 COL 1.
             ACCEPT PLUNCHA AT LINE 10 COL 35.
             DISPLAY "ENTER CONVEYANCE :" AT LINE 11 COL 1.
             ACCEPT PCONVEY AT LINE 11 COL 35.
             DISPLAY "ENTER BUSINESS ATTIREMENT :" AT LINE 12 COL 1.
             ACCEPT PBUSATR AT LINE 12 COL 35.
             DISPLAY "ENTER LEAVE TRAVEL ALLOWANCE :" AT LINE 13 COL 1.
             ACCEPT PLTA AT LINE 13 COL 35.
             DISPLAY "ENTER PF :" AT LINE 14 COL 1.
             ACCEPT PPF AT LINE 14 COL 35.
             DISPLAY "ENTER ESI :" AT LINE 15 COL 1.
             ACCEPT PESI AT LINE 15 COL 35.
             DISPLAY "ENTER GRATUITY :" AT LINE 16 COL 1.
             ACCEPT PGRTY AT LINE 16 COL 35.
             DISPLAY "ENTER PROFESSIONAL TAX :" AT LINE 17 COL 1.
             ACCEPT PPTAX AT LINE 17 COL 35.
             DISPLAY "ENTER INCOME TAX :" AT LINE 18 COL 1.
             ACCEPT PITAX AT LINE 18 COL 35.
             DISPLAY "ENTER LOAN :" AT LINE 19 COL 1.
             ACCEPT PLOAN AT LINE 19 COL 35.
             DISPLAY "ENTER LOAN DEDUCTION AMOUNT :" AT LINE 20 COL 1.
             ACCEPT PLOANDA AT LINE 20 COL 35.
             DISPLAY "ENTER OTHER DEDUCTION :" AT LINE 21 COL 1.
             ACCEPT POTHERD AT LINE 21 COL 35.
             DISPLAY "ENTER PERFORMANCE INCENTIVE :" AT LINE 22 COL 1.
             ACCEPT PPERINC AT LINE 22 COL 35.
             DISPLAY "ENTER MEDICAL REIMBURSEMENT :" AT LINE 23 COL 1.
             ACCEPT PMEDI AT LINE 23 COL 35.
             DISPLAY "ENTER BOOK REIMBURSEMENT :" AT LINE 24 COL 1.
             ACCEPT PBOOK AT LINE 24 COL 35.
             DISPLAY ERASE-1
             DISPLAY "ENTER ENTERTAINMENT :" AT LINE 1 COL 1.
             ACCEPT PENTER AT LINE 1 COL 35.
             DISPLAY "ENTER PHONE :" AT LINE 2 COL 1.
             ACCEPT PTPH AT LINE 2 COL 35.
             DISPLAY "ENTER HOUSE RELATED :" AT LINE 3 COL 1.
             ACCEPT PHOUSE AT LINE 3 COL 35.
             DISPLAY "ENTER VEHICLE MAINTENANCE :" AT LINE 4 COL 1.
             ACCEPT PVEHMAN AT LINE 4 COL 35.
             DISPLAY "ENTER CREDIT CARD :" AT LINE 5 COL 1.
             ACCEPT PCREDIT AT LINE 5 COL 35.
             DISPLAY "ENTER CLUB :" AT LINE 6 COL 1.
             ACCEPT PCLUB AT LINE 6 COL 35.
             DISPLAY "ENTER CLUB :" AT LINE 7 COL 1.
             ACCEPT PCLUB AT LINE 7 COL 35.
             DISPLAY "ENTER CLUB :" AT LINE 8 COL 1.
             ACCEPT PCLUB AT LINE 8 COL 35.
             DISPLAY "ENTER CASUAL LEAVE :" AT LINE 9 COL 1.
             ACCEPT PCL AT LINE 9 COL 35.
             DISPLAY "ENTER SICK LEAVE :" AT LINE 10 COL 1.
             ACCEPT PSL AT LINE 10 COL 35.
             DISPLAY "ENTER PAID LEAVE :" AT LINE 11 COL 1.
             ACCEPT PPL AT LINE 11 COL 35.
             DISPLAY "ENTER LEAVE LOSS OF PAY :" AT LINE 12 COL 1.
             ACCEPT PLLOP AT LINE 12 COL 35.
             DISPLAY "ENTER OTHER LEAVES :" AT LINE 13 COL 1.
             ACCEPT POTHERL AT LINE 13 COL 35.
             WRITE PAYMENTREC.
             CLOSE PAYMENTFILE.
             GO TO MAIN-PARA.

         CONFIRMATION-PARA.
             DISPLAY ERASE-1
             OPEN I-O CONFIRMATIONFILE.
             *>IF FSC = 30
             IF FSC <> 00
                OPEN OUTPUT CONFIRMATIONFILE.
             DISPLAY "ENTER CONFIRMATION CODE :" AT LINE 1 COL 1.
             ACCEPT CCONID AT LINE 1 COL 35.
             DISPLAY "ENTER EMP CODE :" AT LINE 2 COL 1.
             ACCEPT CEMPID AT LINE 2 COL 35.
             DISPLAY "ENTER CONFIRMATION DATE :" AT LINE 3 COL 1.
             ACCEPT CCDATE AT LINE 3 COL 35.
             WRITE CONFIRMATIONREC.
             CLOSE CONFIRMATIONFILE.
             GO TO MAIN-PARA.

         GRADE-PARA.
             DISPLAY ERASE-1
             OPEN EXTEND GRADEFILE.
             IF FSG <> 00
             	OPEN OUTPUT GRADEFILE.
             DISPLAY "ENTER GRADE NO. :" AT LINE 1 COL 1.
             ACCEPT GGRADE AT LINE 1 COL 35.
             DISPLAY "ENTER DESIGNATION :" AT LINE 2 COL 1.
             ACCEPT GDESIGN AT LINE 2 COL 35.
             WRITE GRADEREC.
             CLOSE GRADEFILE.
             GO TO MAIN-PARA.

         TRANSFER-PARA.
             DISPLAY ERASE-1
             OPEN I-O TRANSFERFILE.
             *>IF FST = 30
             IF FST <> 00
                OPEN OUTPUT TRANSFERFILE.
             DISPLAY "ENTER TRANSFER CODE :" AT LINE 1 COL 1.
             ACCEPT TTRFID AT LINE 1 COL 35.
             DISPLAY "ENTER EMP CODE :" AT LINE 2 COL 1.
             ACCEPT TEMPID AT LINE 2 COL 35.
             DISPLAY "ENTER OLD BRANCH CODE :" AT LINE 3 COL 1.
             ACCEPT TOBRID AT LINE 3 COL 35.
             DISPLAY "ENTER TRANSFER DATE :" AT LINE 4 COL 1.
             ACCEPT TTRFDT AT LINE 4 COL 35.
             WRITE TRANSFERREC.
             CLOSE TRANSFERFILE.
             GO TO MAIN-PARA.

         EMPPERSONAL-PARA.
             DISPLAY ERASE-1
             OPEN I-O EMPPERSONALFILE.
             *>IF FSEP = 30
             IF FSEP <> 00
                OPEN OUTPUT EMPPERSONALFILE.
             DISPLAY "ENTER EMP CODE :" AT LINE 1 COL 1.
             ACCEPT EPEMPID AT LINE 1 COL 35.
             DISPLAY "ENTER TEMP ADDRESS :" AT LINE 2 COL 1.
             ACCEPT EPTADD AT LINE 2 COL 35.
             DISPLAY "ENTER PHONE :" AT LINE 3 COL 1.
             ACCEPT EPTPH AT LINE 3 COL 35.
             DISPLAY "ENTER DOB :" AT LINE 4 COL 1.
             ACCEPT EPDOB AT LINE 4 COL 35.
             DISPLAY "ENTER POB :" AT LINE 5 COL 1.
             ACCEPT EPPOB AT LINE 5 COL 35.
             DISPLAY "ENTER LANGUAGE KNOWN :" AT LINE 6 COL 1.
             ACCEPT EPLANG AT LINE 6 COL 35.
             DISPLAY "ENTER BLOOD GROUP :" AT LINE 7 COL 1.
             ACCEPT EPBLOOD AT LINE 7 COL 35.
             DISPLAY "ENTER WEIGHT :" AT LINE 8 COL 1.
             ACCEPT EPWEIGHT AT LINE 8 COL 35.
             DISPLAY "ENTER HEIGHT :" AT LINE 9 COL 1.
             ACCEPT EPHEIGHT AT LINE 9 COL 35.
             DISPLAY "ENTER VISION :" AT LINE 10 COL 1.
             ACCEPT EPVISION AT LINE 10 COL 35.
             DISPLAY "ENTER FATHER'S NAME :" AT LINE 11 COL 1.
             ACCEPT EPFATHER AT LINE 11 COL 35.
             DISPLAY "ENTER DOB OF FATHER :" AT LINE 12 COL 1.
             ACCEPT EPDOBF AT LINE 12 COL 35.
             DISPLAY "ENTER MOTHER'S NAME :" AT LINE 13 COL 1.
             ACCEPT EPMOTHER AT LINE 13 COL 35.
             DISPLAY "ENTER DOB OF MOTHER :" AT LINE 14 COL 1.
             ACCEPT EPDOBM AT LINE 14 COL 35.
             DISPLAY "ENTER SPOUSE NAME :" AT LINE 15 COL 1.
             ACCEPT EPSPOUSE AT LINE 15 COL 35.
             DISPLAY "ENTER CHILD NAME :" AT LINE 16 COL 1.
             ACCEPT EPCHILD AT LINE 16 COL 35.
             DISPLAY "ENTER DOB OF CHILD :" AT LINE 17 COL 1.
             ACCEPT EPDOBC AT LINE 17 COL 35.
             WRITE EMPPERSONALREC.
             CLOSE EMPPERSONALFILE.
             GO TO MAIN-PARA.
       END PROGRAM EMPWRITE.


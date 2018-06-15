Dim pndefPath : pndefPath = "D:\dev\pdsys\doc\refs\erp.xlsx"
Dim semuPath : semuPath = "D:\dev\pdsys\doc\refs\semu.xlsx"

Dim excelApp, excelBook, excelBookSemu, excelSheet, excelSheetSemu, logSheet
Set excelApp  =  CreateObject("Excel.Application")
'excelApp.DisplayAlerts   =  False
excelApp.Visible = True
Set excelBook = excelApp.Workbooks.Open(pndefPath)
Set excelBookSemu = excelApp.Workbooks.Open(semuPath)

Set excelSheet = excelBook.Worksheets(1)
Set logSheet = excelBook.Worksheets(2)
Set excelSheetSemu = excelBookSemu.Worksheets(1)

Dim isOK
isOK = true
Stop

Dim janDic
Set janDic = GetJans(excelSheet)

Dim logLine : logLine = 1
Dim lineNo : lineNo = 3
Dim prevSehao, prevYanse
Do While True
	lineNo = lineNo + 1

	Dim jan, janArr
	jan = excelSheetSemu.Cells(lineNo, 2).Value
	If jan = "" Or jan = Empty Then
		msgbox "end at line:" & lineNo
		Exit Do
	End If

	Dim sehao
	sehao = excelSheetSemu.Cells(lineNo, 3).Value
	If sehao = "" Then
		sehao = prevSehao
	End If
	prevSehao = sehao

	Dim yanse
	yanse = excelSheetSemu.Cells(lineNo, 4).Value
	If yanse = "" Then
		yanse = prevYanse
	End If
	prevYanse = yanse

	Dim semu
	semu = excelSheetSemu.Cells(lineNo, 7).Value

	Dim yongliang
	yongliang = excelSheetSemu.Cells(lineNo, 11).Value

	janArr = Split(jan, "#")
	Dim i
	For i = LBound(janArr) To UBound(janArr)
		If janArr(i) <> "" Then
			If janDic.Exists(janArr(i)) Then
				If InsertSemu(janArr(i), sehao, yanse, semu, yongliang, excelSheet) = False Then
					logSheet.Cells(logLine, 1).Value = "err jan line:" & lineNo
					logLine = logLine + 1
					isOK = False
					Exit For
				End If
			Else
				logSheet.Cells(logLine, 1).Value = "not exist jan:" & janArr(i)
				logLine = logLine + 1
			End If
		End If
	Next

	If isOK = False Then
		Exit Do
	End If
Loop

If isOK Then
	excelBook.SaveAs pndefPath
End If
excelBook.close
excelBookSemu.Close
excelApp.Quit

Set excelApp=nothing
Set excelBook=nothing
Set excelSheet=nothing
msgbox "finished"

Function GetJans(sheet)
	Dim lineNo : lineNo = 4
	Dim retDic : Set retDic = CreateObject("Scripting.Dictionary")

	Do While True
		Dim bb
		bb = sheet.Cells(lineNo, 3).Value
		If bb = "" Or bb = Empty Then
			Exit Do
		End If

		Dim janCode
		janCode = sheet.Cells(lineNo, 1).Value
		If janCode <> "" And janCode <> empty Then
			If retDic.Exists(Right(janCode,4)) = False Then
				retDic.Add Right(janCode,4), janCode
			End If
		End If

		lineNo = lineNo + 1
	Loop

	Set GetJans = retDic
End Function

Function InsertSemu(jan, sehao, yanse, semu, yongliang, excelSheet)
	InsertSemu = False

	Dim lineNo : lineNo = 4
	Dim isCheckEmpty: isCheckEmpty = False
	Do While True
		Dim janCode
		janCode = excelSheet.Cells(lineNo, 1).Value
		If (janCode = "" Or janCode = empty) And isCheckEmpty Then
			Exit Do
		End If

		Dim bb
		bb = excelSheet.Cells(lineNo, 3).Value
		If bb = "" Or bb = Empty Then
			InsertSemu = True 'not exist semu
			Exit Do
		End If

		Dim janCodeEnd : janCodeEnd = lineNo
		If InStr(janCode, jan) > 0 Then
			For janCodeEnd = lineNo + 1 To lineNo + 1000
				Dim janCodeTmp
				janCodeTmp = excelSheet.Cells(janCodeEnd, 1).Value
				If janCodeTmp <> "" Then
					Exit For
				End If
			Next

			janCodeEnd = janCodeEnd -1
			Dim j
			For j = lineNo To janCodeEnd
				Dim benti, num
				benti = excelSheet.Cells(j, 3).Value
				num = excelSheet.Cells(j, 7).Value
				If benti <> "--" And benti <> "" Then
					If InStr(benti, yanse) > 0 Then
						excelSheet.Rows(j+1).insert
						excelSheet.Rows(j+1).Interior.ColorIndex = 3
						excelSheet.Cells(j+1, 3).Value = benti
						excelSheet.Cells(j+1, 7).Value = num
						excelSheet.Cells(j+1, 8).Value = "å¥"
						excelSheet.Cells(j+1, 9).Value = semu
						excelSheet.Cells(j+1, 10).Value = benti & " êFïÍ"
						excelSheet.Cells(j+1, 11).Value ="åˆã“"
						excelSheet.Cells(j+1, 12).Value = "çé"
						excelSheet.Cells(j+1, 13).Value = 1000
						excelSheet.Cells(j+1, 14).Value = yongliang*num/1000
						excelSheet.Cells(j+1, 15).Value = 1000
						excelSheet.Cells(j+1, 16).Value = "XXX"

						j = j + 1
						janCodeEnd=janCodeEnd+1
						InsertSemu = True
						isCheckEmpty = True
						Exit For
					End If
				End If
			Next
			Exit Do
		End If
		lineNo = janCodeEnd + 1
	Loop

	If InsertSemu = False Then
		Msgbox "invalid jan:" & jan & " sehao:" & sehao
	End if
End Function
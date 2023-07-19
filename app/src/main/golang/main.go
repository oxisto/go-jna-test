package main

import (
	"C"
	"strings"
	"unsafe"

	pointer "github.com/mattn/go-pointer"
)
import "go/ast"

type Simple struct {
	a C.int
	b C.int
}

//export Hello
func Hello(msg string) *C.char {
	return C.CString("hello " + strings.ToUpper(msg))
}

//export GetFuncDecl
func GetFuncDecl() unsafe.Pointer {
	return pointer.Save(
		&ast.FuncDecl{
			Name: ast.NewIdent("test"),
		},
	)
}

//export GetFuncDeclName
func GetFuncDeclName(ptr unsafe.Pointer) unsafe.Pointer {
	decl := pointer.Restore(ptr).(*ast.FuncDecl)
	return pointer.Save(decl.Name)
}

//export GetIdentName
func GetIdentName(ptr unsafe.Pointer) *C.char {
	id := pointer.Restore(ptr).(*ast.Ident)
	return C.CString(id.Name)
}

//export GetA
func GetA(ptr unsafe.Pointer) C.int {
	simple := pointer.Restore(ptr).(*Simple)
	return simple.a
}

func main() {}

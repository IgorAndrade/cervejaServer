package br.com.cerveja.exception;

public enum RN {
	CERVEJA_NAO_ECONTRADA("cerveja.naoecontrada"),
	ERRO_UPLOAD("erro.upload"),
	EMAIL_NULL("erro.usuario.email.null"),
	EMAIL_INVALIDO("erro.usuario.email.invalido"),
	NOME_NULL("erro.usuario.nome.null"),
	SENHA_NULL("erro.usuario.senha"),
	USER_REPETIDO("erro.usuario.email.repetido"),
	USER_NOT_FOUND("erro.usuario.naoencontrado");
	
	private String msg;
	
	private RN(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public static RN valueOfMsg(String msg){
		for(RN rn : values()){
			if(rn.getMsg().equalsIgnoreCase(msg))
				return rn;
		}
		return null;
	}
}

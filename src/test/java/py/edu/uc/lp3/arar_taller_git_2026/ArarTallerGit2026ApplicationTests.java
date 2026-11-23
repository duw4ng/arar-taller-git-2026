package py.edu.uc.lp3.arar_taller_git_2026;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ArarTallerGit2026ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void openApiJsonSeGeneraConContratoCompleto() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.info.title").value("Minecraft API — POO-09"))
				.andExpect(jsonPath("$.paths['/minecraft/aldeanos']").exists())
				.andExpect(jsonPath("$.paths['/minecraft/aldeanos/{id}']").exists())
				.andExpect(jsonPath("$.paths['/minecraft/simulaciones/explosion']").exists())
				.andExpect(jsonPath("$.paths['/minecraft/danables']").exists())
				.andExpect(jsonPath("$.paths['/minecraft/escenarios/explosiones-cruzadas']").exists())
				.andExpect(jsonPath("$.paths['/minecraft/escenarios/diagnostico']").exists());
	}

	@Test
	void openApiYamlSeGenera() throws Exception {
		mockMvc.perform(get("/v3/api-docs.yaml"))
				.andExpect(status().isOk());
	}

}

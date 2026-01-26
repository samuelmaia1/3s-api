package com._s.api.presentation.controllers;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.domain.product.service.CreateProductService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.CreateUserService;
import com._s.api.domain.user.service.GetUserService;
import com._s.api.presentation.dto.CreateProductRequest;
import com._s.api.presentation.dto.CreateUserRequest;
import com._s.api.presentation.mapper.product.ProductRequestMapper;
import com._s.api.presentation.mapper.product.ProductResponseMapper;
import com._s.api.presentation.mapper.user.UserRequestMapper;
import com._s.api.presentation.mapper.user.UserResponseMapper;
import com._s.api.presentation.response.ProductResponse;
import com._s.api.presentation.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@Tag(
        name = "Usuários",
        description = "Endpoints responsáveis pelo gerenciamento de usuários"
)
public class UserController {

    private final CreateUserService createUserService;
    private final GetUserService getUserService;
    private final CreateProductService createProductService;

    public UserController(CreateUserService createUserService,
                          GetUserService getUserService,
                          CreateProductService createProductService)
    {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
        this.createProductService = createProductService;
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário com base nos dados informados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos enviados na requisição",
                    content = @Content
            )
    })
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @Valid
            @RequestBody
            @Schema(description = "Dados necessários para criação do usuário")
            CreateUserRequest request
    ) {
        CreateUserCommand command = UserRequestMapper.toCommand(request);

        User user = createUserService.execute(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseMapper.toResponse(user));
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário a partir do seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(
                    description = "ID do usuário",
                    example = "b3b9c2f1-9f5c-4e8d-a0f3-123456789abc"
            )
            @PathVariable String id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponseMapper.toResponse(getUserService.executeById(id)));
    }

    @Operation(
            summary = "Criar produto para um usuário",
            description = "Cria um novo produto associado a um usuário existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos enviados na requisição",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{userId}/products")
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(
                    description = "ID do usuário ao qual o produto será associado",
                    example = "b3b9c2f1-9f5c-4e8d-a0f3-123456789abc"
            )
            @PathVariable String userId,

            @Valid
            @RequestBody
            @Schema(description = "Dados necessários para criação do produto")
            CreateProductRequest data
    ) {
        CreateProductCommand command = ProductRequestMapper.toCommand(data);

        Product product = createProductService.execute(command, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponseMapper.toResponse(product));
    }

}

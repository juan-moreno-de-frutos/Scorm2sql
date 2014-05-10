package es.uah.ieru.scorm2sql;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;



/**
 * @author Juan Moreno de Frutos
 * 
 */

public class TratarManifest {

        Namespace lomes,adlcp,xsi,xmlns;

	public BeanManifest leer (Document doc, String name) throws ExceptionManifest,ExceptionChildrenManifest{
		BeanManifest manifest = null;
                
		
		try {
			ValidadorEntradas validador = new ValidadorEntradas();
                        Element raiz = doc.getRootElement();
                        Scorm2sql.logger.debug("ROOT: "+raiz.getName());
			if(!raiz.getName().equals("manifest"))
                         {   
                             throw new ExceptionManifest("File XML hasn't manifest.");
                         }else{
                                
				manifest = new BeanManifest();
                                
                                //process namespaces
                                xmlns = raiz.getNamespace();   
                                Scorm2sql.logger.debug("Namespace:"+xmlns);
                                adlcp = raiz.getNamespace("adlcp");  
                                Scorm2sql.logger.debug("Namespace:"+adlcp);
                                lomes = raiz.getNamespace("lomes"); 
                                Scorm2sql.logger.debug("Namespace:"+lomes);
                                xsi = raiz.getNamespace("xsi");
                                Scorm2sql.logger.debug("Namespace:"+xsi);
                                
    				//process of the attributes
				String id = raiz.getAttributeValue("identifier");
                                Scorm2sql.logger.debug("Manifest_Identifier:"+id);
                                if(id == null || id.equals("")){                                        
					throw new ExceptionManifest("Manifest hasn't identifier.");                                        
				 }else{ if(!validador.ValidarId(id)){                                        
                                            throw new ExceptionManifest("Incorrect Manifest_Identifier.");
                                        }
                                   }
                                manifest.setIdentifier(id);
                                
                                String base = raiz.getAttributeValue("base",Namespace.XML_NAMESPACE);                                
                                Scorm2sql.logger.debug("Manifest_base:"+base);
                                manifest.setBase(base);
                                
				String version = raiz.getAttributeValue("version");
                                Scorm2sql.logger.debug("Manifest_version:"+version);
                                if ((version != null)&&(!validador.ValidarVersion(version)))
                                    {Scorm2sql.logger.info("Incorrect Manifest_Version.");
                                    }else{manifest.setVersion(version);}
                                                             	
				
                                
                                Scorm2sql.logger.debug("Atributes BeanManifest corrects.");
				
				//process nodos hijos
				List<Element> hijos = raiz.getChildren();
                                Scorm2sql.logger.debug("List Elements created.");
				tratarHijos(hijos,manifest, name);
                                Scorm2sql.logger.debug("Elements BeanManifest corrects.");
			}
		
                
                }catch(ExceptionItem e){
                        MensajeError.mostrar("Problems reading a Item: " + e.getMistake());
                        Scorm2sql.logger.info("Problems reading a Item: " + e.getMistake());
                        return null;
                }catch(ExceptionMetadata e){
                        MensajeError.mostrar("Problems reading a Metadata: " + e.getMistake());
                        Scorm2sql.logger.info("Problems reading a Metadata: " + e.getMistake());
                        return null;
                }catch(ExceptionResources e){
//                        MensajeError.mostrar("Problems reading a Resources: " + e.getMistake());
                        Scorm2sql.logger.info("Problems reading a Resources: " + e.getMistake());
                        return null;
                }catch(ExceptionOrganizations e){
                        MensajeError.mostrar("Problems reading a Organizations: " + e.getMistake());
                        Scorm2sql.logger.info("Problems reading a Organizations: " + e.getMistake());
                        return null;
                }catch (ExceptionChildrenManifest e){                      
//			MensajeError.mostrar( e.getMistake());
                        Scorm2sql.logger.info( e.getMistake());  
                        return null;
                }catch (ExceptionManifest e){                      
//			MensajeError.mostrar("Problems reading a Manifest: " + e.getMistake());
                        Scorm2sql.logger.info( e.getMistake()); 
                        return null;

                }
                
                manifest.setId(1);// All correct.
		return manifest;
	}

	private void tratarHijos(List<Element> hijos,BeanManifest manifest, String name) throws ExceptionChildrenManifest,ExceptionResources,ExceptionMetadata,ExceptionOrganizations{
            
            int md=-1;
            Element hijo2;
            ValidadorEntradas validador = new ValidadorEntradas();
            try {
		for (int i = 0;i<hijos.size();i++){
			Element hijo = hijos.get(i);
			String nombreHijo = hijo.getName();                        
			if(nombreHijo.equals("metadata")){
                            
                                Scorm2sql.logger.debug("Element is a Metadata");
                                BeanMetadata metadata = new BeanMetadata();                            
                                
                                
                                hijo2 = hijo.getChild("schema", xmlns);
                                if (hijo2!= null){
                                    String schema = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_schema:"+schema);
                                    if ((schema != null)&&(!validador.ValidarSchema(schema)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_Schema.");
                                        }else{metadata.setSchema(schema);}
                                }                                
                                
                                hijo2 = hijo.getChild("schemaversion", xmlns);
                                if (hijo2!= null){
                                    String schemaVer = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_schemaversion:"+schemaVer);
                                    if ((schemaVer != null)&&(!validador.ValidarSchemaVersion(schemaVer)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_SchemaVersion.");
                                        }else{metadata.setSchemaversion(schemaVer);}
                                }                                
                                
                                hijo2 = hijo.getChild("location", adlcp);
                                if (hijo2!= null){
                                    String location = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_location:"+location);
                                    if ((location != null)&&(!validador.ValidarLocation(location)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_Location.");
                                        }else{metadata.setLocation(location);}
                                }                                                             
                                
                                hijo2 = hijo.getChild("lom", lomes);
                                if (hijo2!= null){
                                    XMLOutputter outp = new XMLOutputter();
                                    String meta_data = outp.outputString(hijo2);                                    
                                    if (meta_data != null ) {md=1;}
//                                    
////                                    System.out.println(meta_data);
//                                    try{
////                                        System.out.println("uso del lom2sql:");
//                                        md=apiLom2sql.insertar(meta_data,name);
//                                        System.out.println("insertado un nuevo lom");
//                                     }catch (Exception e){
//                                         MensajeError.mostrar("Problems inserting lom metadata. " + e.getMessage());
//                                         Scorm2sql.logger.debug("Problems inserting lom metadata. " + e.getMessage());   
//                                      }
                                    metadata.setMeta_data(md);
                                    Scorm2sql.logger.debug("Metadata_meta-data:"+md);
                                     
                                }                                
                           
                                List<Element> hijosMetadata= hijo.getChildren();
                                metadata.setType("manifest");
			        
                                if (hijosMetadata != null){ tratarMetadata(hijosMetadata, metadata);}
				manifest.setMetadata(metadata);
                                
                                
                                
			}else if (nombreHijo.equals("organizations")) {
                                
                                BeanOrganizations organizations = new BeanOrganizations();
                                Scorm2sql.logger.debug("Element is a Organizations");
                                
                                String defaul = hijo.getAttributeValue("default");
                                Scorm2sql.logger.debug("Organizations_defult: "+defaul);
                                if ((defaul != null)&&(!validador.ValidarIdRef(defaul)))
                                        {Scorm2sql.logger.info("Incorrect Organizations_default.");
                                        }else{organizations.setDefaul(defaul);}
                                
				List<Element> hijosOrganizations = hijo.getChildren();
				if(hijosOrganizations == null){
					throw new ExceptionChildrenManifest("organizations node has not elements.");
				 }
                                                                    
                                
				tratarOrganizations(hijosOrganizations, organizations, name);
				manifest.setOrganizations(organizations);
                                
                                
				
			}else if (nombreHijo.equals("resources")) {
                            
                                Scorm2sql.logger.debug("Element is a Resources");
                                String base = hijo.getAttributeValue("base", Namespace.XML_NAMESPACE);
                                Scorm2sql.logger.debug("Resources_base:"+base);
				List<Element> hijosResources= hijo.getChildren();
				if(hijosResources == null){
                                    Scorm2sql.logger.debug("Resources node has not elements.");                                    
				}else{
                                
                                    BeanResources resources = new BeanResources();
                                    resources.setBase(base);                                    

                                    tratarResources(hijosResources,resources, name);
                                    manifest.setResources(resources);
                                }
                                
                                
                                
                        }else if (nombreHijo.equals("manifest")) {
                            
                                Scorm2sql.logger.debug("Element is a Manifest");
//				List<Element> hijosManifestsub = hijo.getChildren();
//				if(hijosManifestsub == null){
//					throw new ExceptionChildrenManifest("manifest node has not elements.");
//				}
				BeanManifest manifestsub = new BeanManifest();
                                
                                String id = hijo.getAttributeValue("identifier");
                                Scorm2sql.logger.debug("Manifest_Identifier:"+id);
                                if(id == null || id.equals("")){                                        
					throw new ExceptionChildrenManifest("Manifest hasn't identifier.");                                        
				 }else{ if(!validador.ValidarId(id)){                                        
                                            throw new ExceptionChildrenManifest("Incorrect Manifest_Identifier.");
                                        }
                                   }
                                manifestsub.setIdentifier(id);
                                
                                String base = hijo.getAttributeValue("base",Namespace.XML_NAMESPACE);                                
                                Scorm2sql.logger.debug("Manifest_base:"+base);
                                manifestsub.setBase(base);
                                
				String version = hijo.getAttributeValue("version");
                                Scorm2sql.logger.debug("Manifest_version:"+version);
                                if ((version != null)&&(!validador.ValidarVersion(version)))
                                    {Scorm2sql.logger.info("Incorrect Manifest_Version.");
                                    }else{manifestsub.setVersion(version);}
                 
                                List<Element> hijosManifestsub = hijo.getChildren();
				tratarHijos(hijosManifestsub, manifestsub, name);
				manifest.setOneManifest(manifestsub);        
                        
                                
			}else {
                                Scorm2sql.logger.debug("The " + nombreHijo + " node isn't part of the structure.");
				throw new ExceptionChildrenManifest("The " + nombreHijo + " node isn't part of the structure.");
			}

		}
                
            }catch (ExceptionChildrenManifest e){                      			
                        Scorm2sql.logger.info("Problems reading a Manifest: " + e.getMistake());  
                        throw new ExceptionChildrenManifest("Problems reading a Manifest: " + e.getMistake());
            }
	}
        
        //Resources
	private void tratarResources(List<Element> hijosResources, BeanResources resources, String name) throws ExceptionResources,ExceptionResource,ExceptionMetadata{
		for (int j = 0;j<hijosResources.size();j++){
			Element hijoResources = hijosResources.get(j);
			String nombreHijo = hijoResources.getName();
                        ValidadorEntradas validador = new ValidadorEntradas();
                        
			if(nombreHijo.equals("resource")){
                                
                                BeanResource resource = new BeanResource();
                                Scorm2sql.logger.debug("Element is a Resource");
                                
				String id = hijoResources.getAttributeValue("identifier");
                                Scorm2sql.logger.debug("Resource_identifier:"+id);
				if(id == null | id.equals("")){
					throw new ExceptionResources("Resources hasn't identifier.");
				}else{ if(!validador.ValidarId(id)){                                        
                                            throw new ExceptionResources("Incorrect Resource_Identifier.");
                                        }
                                   }
                                
                                String href = hijoResources.getAttributeValue("href");
                                Scorm2sql.logger.debug("Resource_href:"+href);
				if((href == null )| (href.equals("")) ){
					throw new ExceptionResources("Resource hasn't href.");
				}else{ if(!validador.ValidarHref(href)){                                        
                                            throw new ExceptionResources("Incorrect Resource_Href.");
                                        }
                                   }
                                
                                String type = hijoResources.getAttributeValue("type");
                                Scorm2sql.logger.debug("Resource_type:"+type);
				if(type == null | type.equals("")){
					throw new ExceptionResources("Resource hasn't type.");
				}else{ if(!validador.ValidarResourceType(type)){                                        
                                            throw new ExceptionResources("Incorrect Resource_Type.");
                                        }
                                   }
                                
                                String scormtype = hijoResources.getAttributeValue("scormtype",adlcp);
                                Scorm2sql.logger.debug("Resource_scormtype:"+scormtype);
                                if ((scormtype != null)&&(!validador.ValidarScormtype(scormtype)))
                                        {Scorm2sql.logger.info("Incorrect Resource_ScormType.");
                                        }else{resource.setScormtype(scormtype); }
                                
				
                                String base = hijoResources.getAttributeValue("base",Namespace.XML_NAMESPACE);
				Scorm2sql.logger.debug("Resource_base:"+base);                                
                                
                                
                                resource.setIdentifier(id);
                                resource.setBase(base);
                                resource.setHref(href);
                                resource.setType(type);
                                                               
                                
                                List<Element> hijosResource = hijoResources.getChildren();
				if(hijosResource == null){
					Scorm2sql.logger.info("Resource node has not elements.");                                        
				} else{
                                
                                    tratarResource(hijosResource, resource, name);		
                                    
                                  }
                                
                                //Scorm2sql.logger.debug("Resource: "+resource.toString());
                                resources.setOneResource(resource);
                                
                                
                                
			} else {
				throw new ExceptionResources("The " + nombreHijo + " node isn't part of the structure of Resource.");
			}
		}
		
	}
        
        
        //Resources
	private void tratarResource(List<Element> hijosResource, BeanResource resource, String name) throws ExceptionResource,ExceptionMetadata{
		for (int j = 0;j<hijosResource.size();j++){
			Element hijoResource = hijosResource.get(j);
			String nombreHijo = hijoResource.getName();
                        int md=0;
                        Element hijo2;
                        ValidadorEntradas validador = new ValidadorEntradas();
			
                        if(nombreHijo.equals("metadata")){
                            
                                BeanMetadata metadata = new BeanMetadata();
                            
                                Scorm2sql.logger.debug("Resource's Element is a Metadata");
                                                               
                                hijo2 = hijoResource.getChild("schema", xmlns);
                                if (hijo2!= null){
                                    String schema = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_schema:"+schema);
                                    if ((schema != null)&&(!validador.ValidarSchema(schema)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_Schema.");
                                        }else{metadata.setSchema(schema);}
                                }
                                
                                hijo2 = hijoResource.getChild("schemaversion", xmlns);
                                if (hijo2!= null){
                                    String schemaVer = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_schemaversion:"+schemaVer);
                                    if ((schemaVer != null)&&(!validador.ValidarSchemaVersion(schemaVer)))                                        
                                        {Scorm2sql.logger.info("Incorrect Metadata_SchemaVersion.");
                                        }else{metadata.setSchemaversion(schemaVer);}
                                }
                               
                                                            
                                hijo2 = hijoResource.getChild("location",adlcp);
                                if (hijo2!= null){
                                    String location = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_location:"+location);
                                    if ((location != null)&&(!validador.ValidarLocation(location)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_Location.");
                                        }else{metadata.setLocation(location);}
                                }
                                                             
                                
                                hijo2 = hijoResource.getChild("lom", lomes);
                                if (hijo2!= null){
                                    XMLOutputter outp = new XMLOutputter();
                                    String meta_data = outp.outputString(hijo2);                                    
                                    if (meta_data != null ) {md=1;}
//                                    XMLOutputter outp = new XMLOutputter();
//                                    String meta_data = outp.outputString(hijo2);
////                                    String meta_data = hijo2.toString();
//                                    try{
//                                        md=apiLom2sql.insertar(meta_data,name);                                            
//                                     }catch (Exception e){
//                                         MensajeError.mostrar("Problems inserting lom metadata. " + e.getMessage());
//                                         Scorm2sql.logger.debug("Problems inserting lom metadata. " + e.getMessage());   
//                                      }
                                    metadata.setMeta_data(md);
                                    Scorm2sql.logger.debug("Metadata_meta-data:"+md);
                                    
                                }                                                               
                                
                                
                                List<Element> hijosMetadata= hijoResource.getChildren();                                                
                                metadata.setType("resource");
                                
				
			        
                                if (hijosMetadata != null){ tratarMetadata(hijosMetadata, metadata);}
                                //Scorm2sql.logger.debug("Metadata: "+metadata.toString());
                                resource.setMetadata(metadata);
                                
                                
                                
                        } else if(nombreHijo.equals("file")){
                            
                                BeanFile file = new BeanFile();
                                Scorm2sql.logger.debug("Resource's Element is a File");
                            
                                String href = hijoResource.getAttributeValue("href");
                                Scorm2sql.logger.debug("File_href:"+href);
                                if ((href != null)&&(!validador.ValidarHref(href)))
                                        {Scorm2sql.logger.info("Incorrect File_href.");
                                        }else{file.setHref(href);}
                                
				List<Element> hijosFile= hijoResource.getChildren();
				                                
				
                                
                                
                                if(hijosFile.isEmpty()){
                                    Scorm2sql.logger.debug("File node has not metadata.");
				 }else{
                                    
                                    //Add metadata elements                                    
                                    Element hijoFile = hijosFile.get(j);
                                    int mdf=0;
			
                                    if(nombreHijo.equals("metadata")){
                                        
                                            Scorm2sql.logger.debug("File's Element is a Metadata");
                                            BeanMetadata metadata = new BeanMetadata();
                                        
                                            
                                                               
                                            hijo2 = hijoFile.getChild("schema", xmlns);
                                            if (hijo2!= null){
                                                String schema = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_schema:"+schema);
                                                if ((schema != null)&&(!validador.ValidarSchema(schema)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_Schema.");
                                                    }else{metadata.setSchema(schema);}
                                            }

                                            hijo2 = hijoFile.getChild("schemaversion", xmlns);
                                            if (hijo2!= null){
                                                String schemaVer = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_schemaversion:"+schemaVer);
                                                if ((schemaVer != null)&&(!validador.ValidarSchemaVersion(schemaVer)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_SchemaVersion.");
                                                    }else{metadata.setSchemaversion(schemaVer);}
                                            }


                                            hijo2 = hijoFile.getChild("location",adlcp);
                                            if (hijo2!= null){
                                                String location = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_location:"+location);
                                                if ((location != null)&&(!validador.ValidarLocation(location)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_Location.");
                                                    }else{metadata.setLocation(location);}
                                            }


                                            hijo2 = hijoFile.getChild("lom", lomes);
                                            if (hijo2!= null){
                                                XMLOutputter outp = new XMLOutputter();
                                                String meta_data = outp.outputString(hijo2);                                                
                                                if (meta_data != null ) {mdf=1;}
                                                
//                                                XMLOutputter outp = new XMLOutputter();
//                                                String meta_data = outp.outputString(hijo2);
//                                                try{
//                                                    mdf=apiLom2sql.insertar(meta_data,name);                                            
//                                                 }catch (Exception e){
//                                                     MensajeError.mostrar("Problems inserting lom metadata. " + e.getMessage());
//                                                     Scorm2sql.logger.debug("Problems inserting lom metadata. " + e.getMessage());   
//                                                  }
                                                
                                                metadata.setMeta_data(mdf);
                                                Scorm2sql.logger.debug("Metadata_meta-data:"+mdf);
                                                 
                                            }                                                               


                                            List<Element> hijosMetadata= hijoFile.getChildren();                                                
                                            metadata.setType("file");
                                            
                                  

                                            if (hijosMetadata != null){ tratarMetadata(hijosMetadata, metadata);}
                                            file.setMetadata(metadata);                                  
                                                                        
                                     }else {
                                        throw new ExceptionResource("The " + nombreHijo + " node isn't part of the structure of File.");
                                     }
                                
                                 }
                                
                                resource.setOneFile(file);                              
                                
                                
                               
                                
                        } else if(nombreHijo.equals("dependency")){
                              
                                String idref = hijoResource.getAttributeValue("identifierref");
                                if(idref == null || idref.equals("")){
					throw new ExceptionResource("Dependency hasn't identifierref.");
				}else{ if(!validador.ValidarIdRef(idref)){                                        
                                            throw new ExceptionResource("Incorrect Dependency_Identifier.");
                                        }
                                   }				                                
				BeanDependency dep = new BeanDependency();
                                dep.setIdentifierref(idref);
                                
                                resource.setOneDependency(dep);
                                
                                                               
                                
                         } else {
                             throw new ExceptionResource("The " + nombreHijo + " node isn't part of the structure.");
                          }
		}
		
	}
        
        
        
        
        //Organizations
        private void tratarOrganizations(List<Element> hijosOrganizations, BeanOrganizations organizations, String name) 
                    throws ExceptionOrganizations,ExceptionOrganization,ExceptionMetadata{
                
                ValidadorEntradas validador = new ValidadorEntradas();
            
		for (int j = 0;j<hijosOrganizations.size();j++){
			Element hijoOrganizations = hijosOrganizations.get(j);
			String nombreHijo = hijoOrganizations.getName();
                        
			if(nombreHijo.equals("organization")){
                                
                                Scorm2sql.logger.debug("Element is a Organization");
                                BeanOrganization organization = new BeanOrganization();
                                Element hijo2;
                                
				String id = hijoOrganizations.getAttributeValue("identifier"); 
                                Scorm2sql.logger.debug("Organization_identifier: "+id);
                                if(id == null | id.equals("")){
					throw new ExceptionOrganizations("Organization hasn't identifier.");
				}else{ if(!validador.ValidarId(id)){                                        
                                            throw new ExceptionOrganizations("Incorrect Organization_Identifier.");
                                        }
                                   }
                                organization.setIdentifier(id); 
                                
                                
                                String structure = hijoOrganizations.getAttributeValue("structure");
                                Scorm2sql.logger.debug("Organiation_structure: "+structure);
                                if ((structure != null)&&(!validador.ValidarStructure(structure)))
                                    {Scorm2sql.logger.info("Incorrect Organization_Structure.");
                                    }else{organization.setStructure(structure);}
                                
                                
                                if ((hijo2=hijoOrganizations.getChild("title",xmlns))!=null){ 
                                   String title = hijo2.getText();
                                   if(!validador.ValidarTitle(title)){                                        
                                        throw new ExceptionOrganizations("Incorrect Organization_Title.");
                                     }
                                   organization.setTitle(title);
                                   Scorm2sql.logger.debug("Organiation_title: "+title);
                                 }else{throw new ExceptionOrganizations("Organization hasn't title.");}   
                                
                                
                                List<Element> hijosOrganization= hijoOrganizations.getChildren();				                             
                                
                                                                
                                tratarOrganization(hijosOrganization, organization, name); 
                                Scorm2sql.logger.debug("Organiation: "+organization.toString());
                                Scorm2sql.logger.debug("Item: "+organization.getItem().toString());
				organizations.setOneOrganization(organization); 
                                
                              
			
			} else {
				throw new ExceptionOrganizations("The " + nombreHijo + " node isn't part of the structure.");
			}
		}
		
	}
        
        
        
        
        
        private void tratarOrganization(List<Element> hijosOrganization, BeanOrganization organization, String name) throws ExceptionOrganization,ExceptionMetadata{
		for (int j = 0;j<hijosOrganization.size();j++){
			Element hijoOrganization = hijosOrganization.get(j);
			String nombreHijo = hijoOrganization.getName();
                        int md=0;
                        Element hijo2;
                        ValidadorEntradas validador = new ValidadorEntradas();
			
                        if(nombreHijo.equals("metadata")){
                            
                                BeanMetadata metadata = new BeanMetadata();
                            
                                Scorm2sql.logger.debug("Organization's Element is a Metadata");
                                                               
                                hijo2 = hijoOrganization.getChild("schema", xmlns);
                                if (hijo2!= null){
                                    String schema = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_schema:"+schema);
                                    if ((schema != null)&&(!validador.ValidarSchema(schema)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_Schema.");
                                        }else{metadata.setSchema(schema);}
                                }
                                
                                hijo2 = hijoOrganization.getChild("schemaversion", xmlns);
                                if (hijo2!= null){
                                    String schemaVer = hijo2.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_schemaversion:"+schemaVer);
                                    if ((schemaVer != null)&&(!validador.ValidarSchemaVersion(schemaVer)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_SchemaVersion.");
                                        }else{metadata.setSchemaversion(schemaVer);}
                                }
                               
                                                            
                                hijo2 = hijoOrganization.getChild("location",adlcp);
                                if (hijo2!= null){
                                    String location = hijoOrganization.getTextNormalize();
                                    Scorm2sql.logger.debug("Metadata_location:"+location);
                                    if ((location != null)&&(!validador.ValidarLocation(location)))
                                        {Scorm2sql.logger.info("Incorrect Metadata_Location.");
                                        }else{metadata.setLocation(location);}
                                }
                                                             
                                
                                hijo2 = hijoOrganization.getChild("lom", lomes);
                                if (hijo2!= null){
                                    XMLOutputter outp = new XMLOutputter();
                                    String meta_data = outp.outputString(hijo2);                                    
                                    if (meta_data != null ) {md=1;}

//                                    XMLOutputter outp = new XMLOutputter();
//                                    String meta_data = outp.outputString(hijo2);
//                                    try{
//                                        md=apiLom2sql.insertar(meta_data,name);                                            
//                                     }catch (Exception e){
//                                         MensajeError.mostrar("Problems inserting lom metadata. " + e.getMessage());
//                                         Scorm2sql.logger.debug("Problems inserting lom metadata. " + e.getMessage());   
//                                      }
                                    
                                    metadata.setMeta_data(md);
                                    Scorm2sql.logger.debug("Metadata_meta-data:"+md);
                                     
                                }                                                               
                                
                               
                                List<Element> hijosMetadata= hijoOrganization.getChildren();
                                metadata.setType("organization");
			        
                                if (hijosMetadata != null){ tratarMetadata(hijosMetadata, metadata);}
				organization.setMetadata(metadata);
                                
                                
                                
                        } else if(nombreHijo.equals("item")){
                            
                             // hijoI element;
                                Scorm2sql.logger.debug("Organization's Element is a Item");
                                Boolean isvisible=null;
                                BeanItem item = new BeanItem();
                                
                                String id = hijoOrganization.getAttributeValue("identifier");
                                Scorm2sql.logger.debug("Item_Identifier:"+id);
                                if(id == null || id.equals("")){
                                    throw new ExceptionOrganization("Item hasn't identifier.");
				}else{ if(!validador.ValidarId(id)){                                        
                                            throw new ExceptionOrganization("Incorrect Item_Identifier.");
                                        }
                                   }
                                
                                String idref = hijoOrganization.getAttributeValue("identifierref");
                                Scorm2sql.logger.debug("Item_IdentifierRef:"+idref);
                                if ((idref != null)&&(!validador.ValidarIdRef(idref)))
                                  {Scorm2sql.logger.info("Incorrect Item_IdentifierRef.");
                                    }else{item.setIdentifierref(idref);}
                                item.setIdentifier(id);
                                
                                String isvis = hijoOrganization.getAttributeValue("isvisible");
                                Scorm2sql.logger.debug("Item_IsVisible:"+isvis);
                                if(isvis != null){
                                    if (isvis.equalsIgnoreCase("true")){ isvisible = true;
                                    }else if(isvis.equalsIgnoreCase("false")){ isvisible = false;
                                        }else { throw new ExceptionOrganization("incorrect data type in isvisible");}
                                }
                                item.setIsvisible(isvisible);                                
                                
                                String param = hijoOrganization.getAttributeValue("parameters");
                                Scorm2sql.logger.debug("Item_Parameters:"+param);
                                if ((param != null)&&(!validador.ValidarParameters(param)))
                                        {Scorm2sql.logger.info("Incorrect Item_Parameters.");
                                        }else{item.setParameters(param);}
                                
                                if ((hijo2=hijoOrganization.getChild("title",xmlns))!=null){                                
                                   String title = hijo2.getText();
                                   Scorm2sql.logger.debug("Item_title:"+title);
                                   if ((title != null)&&(!validador.ValidarTitle(title)))
                                        {Scorm2sql.logger.info("Incorrect Item_Title.");
                                        }else{item.setTitle(title);}
                                }
                               
                                if ((hijo2=hijoOrganization.getChild("maxtimeallowed",adlcp))!=null){                                
                                   String maxtimallow = hijo2.getText();
                                   Scorm2sql.logger.debug("Item_maxtimeallowed:"+maxtimallow);
                                   if ((maxtimallow != null)&&(!validador.ValidarMaxTimeAllowed(maxtimallow)))
                                        {Scorm2sql.logger.info("Incorrect Item_MaxTimeAllowed.");
                                        }else{item.setMaxtimeallowed(maxtimallow);}
                                }
                                
                                if ((hijo2=hijoOrganization.getChild("timelimitaction",adlcp))!=null){                                
                                   String timelimitact= hijo2.getText();
                                   Scorm2sql.logger.debug("Item_timelimitaction:"+timelimitact);
                                   if ((timelimitact != null)&&(!validador.ValidarTimeLimitAction(timelimitact)))
                                        {Scorm2sql.logger.info("Incorrect Item_TimeLimitAction.");
                                        }else{item.setTimelimitaction(timelimitact);}
                                }
                                
                                if ((hijo2=hijoOrganization.getChild("datafromlms",adlcp))!=null){                                
                                   String datfromlms= hijo2.getText();
                                   Scorm2sql.logger.debug("Item_datafomlms:"+datfromlms);
                                   if ((datfromlms != null)&&(!validador.ValidarDataFromLms(datfromlms)))
                                        {Scorm2sql.logger.info("Incorrect Item_DataFromLMS.");
                                        }else{item.setDatafromlms(datfromlms);}
                                }
                                
                                if ((hijo2=hijoOrganization.getChild("masteryscore",adlcp))!=null){                                
                                   String mastscor= hijo2.getText();
                                   Scorm2sql.logger.debug("Item_masteryscore:"+mastscor);
                                   if ((mastscor != null)&&(!validador.ValidarMasteryscore(mastscor)))
                                        {Scorm2sql.logger.info("Incorrect Item_MasteryScore.");
                                        }else{item.setMasteryscore(mastscor);}
                                }
                                
                                if ((hijo2=hijoOrganization.getChild("prerequisites",adlcp))!=null){                                
                                   String prerequisites= hijo2.getText();
                                   Scorm2sql.logger.debug("Item_Prerequisites:"+prerequisites);
                                   if (prerequisites != null) {
                                       if(!validador.ValidarPrerequisites(prerequisites))
                                            {Scorm2sql.logger.info("Incorrect Item_Prerequisites.");}
                                         else {item.setPrerequisites(prerequisites);}
                                       
                                       String prereq_type= hijo2.getAttributeValue("type");
                                       Scorm2sql.logger.debug("Item_Prerequisites_type:"+prereq_type);
                                       if ((prereq_type != null)&&(!validador.ValidarPrerequisiteType(prereq_type)))
                                        {Scorm2sql.logger.info("Incorrect Prerequisites_Type.");
                                        }else{item.setPrerequisites_type(prereq_type);}
                                   }
                                }
                                
				List<Element> hijosItem= hijoOrganization.getChildren();
				                                
			 
                                
                                if(hijoOrganization.getChild("metadata",xmlns) == null){
                                    Scorm2sql.logger.debug("item node has not metadata.");
				 }else{
                                    //Add metadata elements                                    
                                    Element hijoItem = hijoOrganization.getChild("metadata",xmlns);
                                    int mdi1=0;
			
                                    if(nombreHijo.equals("metadata")){
                                        
                                            BeanMetadata metadata = new BeanMetadata();
                                        
                                            Scorm2sql.logger.debug("Item's Element is a Metadata");
                                                               
                                            hijo2 = hijoItem.getChild("schema", xmlns);
                                            if (hijo2!= null){
                                                String schema = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_schema:"+schema);
                                                if ((schema != null)&&(!validador.ValidarSchema(schema)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_Schema.");
                                                 }else{metadata.setSchema(schema);}
                                            }

                                            hijo2 = hijoItem.getChild("schemaversion", xmlns);
                                            if (hijo2!= null){
                                                String schemaVer = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_schemaversion:"+schemaVer);
                                                if ((schemaVer != null)&&(!validador.ValidarSchemaVersion(schemaVer)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_SchemaVersion.");
                                                 }else{metadata.setSchemaversion(schemaVer);}
                                            }


                                            hijo2 = hijoItem.getChild("location",adlcp);
                                            if (hijo2!= null){
                                                String location = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_location:"+location);
                                                if ((location != null)&&(!validador.ValidarLocation(location)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_Location.");
                                                 }else{metadata.setLocation(location);}
                                            }


                                            hijo2 = hijoItem.getChild("lom", lomes);
                                            if (hijo2!= null){
                                                XMLOutputter outp = new XMLOutputter();
                                                String meta_data = outp.outputString(hijo2);                                                
                                                metadata.setMeta_data(mdi1);
//                                                
//                                                XMLOutputter outp = new XMLOutputter();
//                                                String meta_data = outp.outputString(hijo2);
//                                                try{
//                                                    mdi1=apiLom2sql.insertar(meta_data,name);                                            
//                                                 }catch (Exception e){
//                                                     MensajeError.mostrar("Problems inserting lom metadata. " + e.getMessage());
//                                                     Scorm2sql.logger.debug("Problems inserting lom metadata. " + e.getMessage());   
//                                                  }
                                                
                                                if (meta_data != null ) {mdi1=1;} 
                                                Scorm2sql.logger.debug("Metadata_meta-data:"+mdi1);
                                            }                                               
                                            
                                            List<Element> hijosMetadata= hijoItem.getChildren();                                            
                                            metadata.setType("item");

                                            if (hijosMetadata != null){ tratarMetadata(hijosMetadata, metadata);}
                                            item.setMetadata(metadata);                                  
                                                                        
                                     }else {
                                        throw new ExceptionOrganization("The " + nombreHijo + " node isn't part of the structure of Item_Metadata.");
                                     }
                                
                                 }
                                
                                
                                tratarSubItem(hijosItem, item, name);

                                organization.setOneItem(item); 
                        } else if(nombreHijo.equals("sequencing")){
                            Scorm2sql.logger.debug("Scorm 2004 detected: Node sequencing");
                            Scorm2sql.logger.info("Scorm 2004 detected. It will be parsered like a 1.2");
                        } else if(!nombreHijo.equals("title")){
                             throw new ExceptionOrganization("The " + nombreHijo + " node isn't part of the structure of Organization.");
                          }
		}
		
	}
        
        
        
        
        
        
        
        private void tratarSubItem(List<Element> hijosItem, BeanItem itemp, String name) throws ExceptionItem,ExceptionMetadata{
                    
		for (int j = 0;j<hijosItem.size();j++){
			Element hijoItem = hijosItem.get(j);                        
			String nombreHijo = hijoItem.getName();
                        Element hijoII;
                        ValidadorEntradas validador = new ValidadorEntradas();
                        
                        
                        
			if(nombreHijo.equals("item")){
                                
                                Scorm2sql.logger.debug("Item's Element is a SubItem");
                                Boolean isvisible=null;
                                BeanItem subItem = new BeanItem();
                                
                                String id = hijoItem.getAttributeValue("identifier");
                                Scorm2sql.logger.debug("SubItem_identifier:"+id);
                                if(id == null || id.equals("")){
                                    throw new ExceptionItem("Item hasn't identifier.");
				}else{ if(!validador.ValidarId(id)){                                        
                                            throw new ExceptionItem("Incorrect SubItem_Identifier.");
                                        }
                                   }
                                subItem.setIdentifier(id);
                                
                                String idref = hijoItem.getAttributeValue("identifierref");
                                Scorm2sql.logger.debug("SubItem_identifierRef:"+idref);
                                if ((idref != null)&&(!validador.ValidarIdRef(idref)))
                                        {Scorm2sql.logger.info("Incorrect SubItem_identifierRef.");
                                        }else{subItem.setIdentifierref(idref);}
                                
                                String isvis = hijoItem.getAttributeValue("isvisible");
                                Scorm2sql.logger.debug("SubItem_IsVisible:"+isvis);                                
                                if(isvis !=null)
                                {
                                    if (isvis.equalsIgnoreCase("true")){ isvisible = true;                                    
                                    }else if(isvis.equalsIgnoreCase("false")){ isvisible = false;
                                        }else { throw new ExceptionItem("incorrect data type in isvisible");}
                                }
                                subItem.setIsvisible(isvisible);
                        
                                String param = hijoItem.getAttributeValue("parameters");
                                Scorm2sql.logger.debug("SubItem_parameters:"+param);
                                if ((param != null)&&(!validador.ValidarParameters(param)))
                                      {Scorm2sql.logger.info("Incorrect SubItem_Param.");
                                        }else{subItem.setParameters(param);}
                                
                                if ((hijoII=hijoItem.getChild("title",xmlns))!=null){                                
                                   String title = hijoII.getText();
                                   Scorm2sql.logger.debug("SubItem_title:"+title);
                                   if ((title != null)&&(!validador.ValidarTitle(title)))
                                      {Scorm2sql.logger.info("Incorrect SubItem_Title.");
                                        }else{subItem.setTitle(title);}
                                }
                               
                                if ((hijoII=hijoItem.getChild("maxtimeallowed",adlcp))!=null){                                
                                   String maxtimallow = hijoII.getText();
                                   Scorm2sql.logger.debug("SubItem_maxtimeallowed:"+maxtimallow);
                                   if ((maxtimallow != null)&&(!validador.ValidarMaxTimeAllowed(maxtimallow)))
                                      {Scorm2sql.logger.info("Incorrect SubItem_maxtimeallowed.");
                                        }else{subItem.setMaxtimeallowed(maxtimallow);}
                                }
                                
                                if ((hijoII=hijoItem.getChild("timelimitaction",adlcp))!=null){                                
                                   String timelimitact= hijoII.getText();
                                   Scorm2sql.logger.debug("SubItem_timelimitaction:"+timelimitact);
                                   if ((timelimitact != null)&&(!validador.ValidarTimeLimitAction(timelimitact)))
                                      {Scorm2sql.logger.info("Incorrect SubItem_TimeLimitAction.");
                                        }else{subItem.setTimelimitaction(timelimitact);}
                                }
                                
                                if ((hijoII=hijoItem.getChild("datafromlms",adlcp))!=null){                                
                                   String datfromlms= hijoII.getText();
                                   Scorm2sql.logger.debug("SubItem_datafromlms"+datfromlms);
                                   if ((datfromlms != null)&&(!validador.ValidarDataFromLms(datfromlms)))
                                      {Scorm2sql.logger.info("Incorrect SubItem_DataFromLMS.");
                                        }else{subItem.setDatafromlms(datfromlms);}
                                }
                                
                                if ((hijoII=hijoItem.getChild("masteryscore",adlcp))!=null){                                
                                   String mastscor= hijoII.getText();
                                   Scorm2sql.logger.debug("SubItem_masteryscore:"+mastscor);
                                   if ((mastscor != null)&&(!validador.ValidarMasteryscore(mastscor)))
                                      {Scorm2sql.logger.info("Incorrect SubItem_MasteryScore.");
                                        }else{subItem.setMasteryscore(mastscor);}
                                }
                                
                                if ((hijoII=hijoItem.getChild("prerequisites",adlcp))!=null){                                
                                   String prerequisites= hijoII.getText();
                                   Scorm2sql.logger.debug("SubItem_prerequisites:"+prerequisites);                                   
                                   if (prerequisites != null){
                                        if(!validador.ValidarPrerequisites(prerequisites))
                                            {Scorm2sql.logger.info("Incorrect SubItem_Prerequisites.");}
                                         else {subItem.setPrerequisites(prerequisites);}
                                        
                                        String prereq_type= hijoII.getAttributeValue("type");
                                        Scorm2sql.logger.debug("SubItem_prerequisites_type:"+prereq_type);
                                        if ((prereq_type != null)&&(!validador.ValidarPrerequisiteType(prereq_type)))
                                            {Scorm2sql.logger.info("Incorrect SubItem_Prerequisites_Type");
                                          }else{subItem.setPrerequisites_type(prereq_type);}
                                   }
                                }
                                                         
                                
				List<Element> hijosSubItem= hijoItem.getChildren();
				 
                                
                                if(hijoItem.getChild("metadata",xmlns) == null){
                                    Scorm2sql.logger.debug("Subitem node has not metadata.");
				 }else{
                                    //Add metadata elements                                    
                                    Element hijoItemAux = hijoItem.getChild("metadata",xmlns);
                                    int mdi=0;
                                    Element hijo2;
			
                                    if(nombreHijo.equals("metadata")){
                                        
                                            BeanMetadata metadata = new BeanMetadata();
                                        
                                            Scorm2sql.logger.debug("Element is a Metadata");
                                                               
                                            hijo2 = hijoItemAux.getChild("schema", xmlns);
                                            if (hijo2!= null){
                                                String schema = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_schema:"+schema);
                                                if ((schema != null)&&(!validador.ValidarSchema(schema)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_Schema.");
                                                  }else{metadata.setSchema(schema);}
                                            }

                                            hijo2 = hijoItemAux.getChild("schemaversion", xmlns);
                                            if (hijo2!= null){
                                                String schemaVer = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_schemaversion:"+schemaVer);
                                                if ((schemaVer != null)&&(!validador.ValidarSchemaVersion(schemaVer)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_SchemaVersion.");
                                                  }else{metadata.setSchemaversion(schemaVer);}
                                            }


                                            hijo2 = hijoItemAux.getChild("location",adlcp);
                                            if (hijo2!= null){
                                                String location = hijo2.getTextNormalize();
                                                Scorm2sql.logger.debug("Metadata_location:"+location);
                                                if ((location != null)&&(!validador.ValidarLocation(location)))
                                                    {Scorm2sql.logger.info("Incorrect Metadata_Location.");
                                                  }else{metadata.setLocation(location);}
                                            }


                                            hijo2 = hijoItemAux.getChild("lom", lomes);
                                            if (hijo2!= null){
                                                XMLOutputter outp = new XMLOutputter();
                                                String meta_data = outp.outputString(hijo2);                                                
                                                metadata.setMeta_data(mdi);
                                                

//                                                XMLOutputter outp = new XMLOutputter();
//                                                String meta_data = outp.outputString(hijo2);
//                                                try{
//                                                    mdi=apiLom2sql.insertar(meta_data,name);                                            
//                                                 }catch (Exception e){
//                                                     MensajeError.mostrar("Problems inserting lom metadata. " + e.getMessage());
//                                                     Scorm2sql.logger.debug("Problems inserting lom metadata. " + e.getMessage());   
//                                                  }
                                                if (meta_data != null ) {mdi=1;} 
                                                Scorm2sql.logger.debug("Metadata_meta-data:"+mdi);
                                            } 
                                            
                                            List<Element> hijosMetadata= hijoItemAux.getChildren();
                                            metadata.setType("item");

                                            if (hijosMetadata != null){ tratarMetadata(hijosMetadata, metadata);}
                                            subItem.setMetadata(metadata);                                  
                                                                        
                                     }else {
                                        throw new ExceptionItem("The " + nombreHijo + " node isn't part of the structure of Item_Metadata.");
                                     }
                                
                                 }//else of "if(hijosFile == null)"
                                
                                
                                tratarSubItem(hijosSubItem, subItem, name); 
                                
                                itemp.setOneItem(subItem); 
                                
                                
                                
			
			} else if((!nombreHijo.equals("title"))&&(!nombreHijo.equals("metadata"))
                                   &&(!nombreHijo.equals("prerequisites"))&&(!nombreHijo.equals("maxtimeallowed"))
                                   &&(!nombreHijo.equals("timelimitaction"))&&(!nombreHijo.equals("datafromlms"))
                                   &&(!nombreHijo.equals("masteryscore")))
                            {if (nombreHijo.equals("presentation")){
                                Scorm2sql.logger.debug("Scorm 2004 detected: Node presentation");
                                Scorm2sql.logger.info("Scorm 2004 detected. It will be parsered like a 1.2");
                              }else{
                                throw new ExceptionItem("The " + nombreHijo + " node isn't part of the structure of Item.");
                              }
                            }
		}
		
	}
        
            
        
        
        
        
       

        
        //It checks all elements are in structure of Metadata.
	private void tratarMetadata(List<Element> hijosMetadata, BeanMetadata metadata) throws ExceptionMetadata{
		for (int j = 0;j<hijosMetadata.size();j++){
			Element hijoMetadata = hijosMetadata.get(j);
			String nombreHijo = hijoMetadata.getName();
			if(nombreHijo.equals("schema")){
                            Scorm2sql.logger.debug("Metadata_schema element finded");
                            
                        }else if(nombreHijo.equals("schemaversion")){
                            Scorm2sql.logger.debug("Metadata_schemaversion element finded");
                          
                          }else  if(nombreHijo.equals("location")){                          
                            
                             Scorm2sql.logger.debug("Metadata_location element finded");
                            }else if(nombreHijo.equals("lom")){
                               Scorm2sql.logger.debug("Metadata_meta-data element finded");
			} else {
				throw new ExceptionMetadata("The " + nombreHijo + " node isn't part of the structure.");
			}
		}
		
	}
	

}
